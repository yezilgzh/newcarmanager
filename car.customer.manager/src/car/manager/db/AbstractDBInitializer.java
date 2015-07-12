package car.manager.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.manager.dao.CommonDao;

public abstract class AbstractDBInitializer extends CommonDao {
	private static Log log = LogFactory.getLog(AbstractDBInitializer.class);
	private static final String DB_INSTALL_FILE = "db_schema.sql";
	private static final String DB_UPGRADE_FILE_PREFIX = "db_schema_upgrade";
	private String initialTable;

	public AbstractDBInitializer(String initialTable) {
		this.initialTable = initialTable;
	}

	private boolean installed(Connection con) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT 1 FROM information_schema.tables WHERE table_name = '"
					+ this.initialTable + "' AND table_type = 'TABLE'");
			rs = ps.executeQuery();

			boolean bool = rs.next();
			return bool;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, rs);
		}
	}

	private void executeScript(Connection con, String filename) {
		PreparedStatement ps = null;
		log.info("执行脚本:" + filename);
		try {
			List<String> sqlList = parseSQL(filename);
			for (String sql : sqlList) {
				log.info("execute:" + sql);
				ps = con.prepareStatement(sql);
				ps.execute();
			}

			con.commit();
		} catch (Throwable e) {
			log.error("执行数据库脚本" + filename + "出错", e);
			throw new RuntimeException(e);
		} finally {
			close(null, ps, null);
		}
	}

	private List<String> parseSQL(String filename) throws Throwable {
		InputStream in = null;
		BufferedReader reader = null;
		try {
			List sqlList = new ArrayList();
			in = getFileInStream(filename);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String temp = null;
			StringBuilder sb = new StringBuilder();
			while ((temp = reader.readLine()) != null) {
				if (temp.trim().startsWith("--")) {
					continue;
				}
				if (temp.trim().endsWith(";")) {
					sb.append(temp);
					sqlList.add(sb.toString());
					sb.setLength(0);
				} else {
					sb.append(temp);
				}
			}
			List localList1 = sqlList;
			return localList1;
		} catch (Throwable e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (reader != null)
				reader.close();
		}

	}

	protected abstract InputStream getFileInStream(String paramString)
			throws Throwable;

	private void install(Connection con) {
		try {
			if (installed(con)) {
				return;
			}
			log.info("安装数据库");
			executeScript(con, "db_schema.sql");
		} catch (RuntimeException e) {
			log.error("安装数据库出错", e);
			throw e;
		}
	}

	public void installAndUpgrade() {
		try {
			Connection con = getConnection();
			installAndUpgrade(con);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void installAndUpgrade(Connection con) {
		try {
			install(con);
			upgrade(con);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(con, null, null);
		}
	}

	private DBVersion findCurrentVersion(Connection con) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT 1 FROM information_schema.tables WHERE table_name = 'DBVERSION' AND table_type = 'TABLE'");
			rs = ps.executeQuery();

			if (!rs.next()) {
				return null;
			}
			DBVersion currentVersion = null;

			ps = con.prepareStatement("select dbversionkey,versionnum,path,desc,createtime from dbversion where dbversionkey = (select max(dbversionkey) from dbversion)");
			rs = ps.executeQuery();
			if (rs.next()) {
				currentVersion = new DBVersion();
				currentVersion.setPrimaryKey(rs.getString("dbversionkey"));
				currentVersion.setVersionNum(rs.getString("versionnum"));
				currentVersion.setPath(rs.getString("path"));
				currentVersion.setCreateTime(rs.getTimestamp("createtime"));
			}
			DBVersion localDBVersion1 = currentVersion;
			return localDBVersion1;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, rs);
		}
	}

	private List<DBVersion> findNotupgradedVersion(DBVersion currentVersion)
			throws Throwable {
		List notupgradedVersions = new ArrayList(1);
		String scriptPostfix = ".sql";
		List<String> nameList = getAllFileName();
		for (String filename : nameList) {
			if ((!filename.startsWith("db_schema_upgrade"))
					|| (!filename.endsWith(scriptPostfix)))
				continue;
			String nameWithoutPrefix = filename.substring("db_schema_upgrade"
					.length());
			String thisVersionNum = nameWithoutPrefix.substring(0,
					nameWithoutPrefix.indexOf(scriptPostfix));
			if ((currentVersion != null)
					&& (!currentVersion.less(thisVersionNum)))
				continue;
			DBVersion notupgradedVersion = new DBVersion();
			notupgradedVersion.setVersionNum(thisVersionNum);
			notupgradedVersion.setPath(getSqlFilePath(filename));
			notupgradedVersion.setDesc("");
			notupgradedVersion
					.setCreateTime(new Timestamp(new Date().getTime()));
			notupgradedVersions.add(notupgradedVersion);
		}

		return notupgradedVersions;
	}

	protected abstract String getSqlFilePath(String paramString);

	protected abstract List<String> getAllFileName() throws Throwable;

	private int addDBVersions(Connection con, DBVersion[] versions) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO dbversion(dbversionkey,versionnum,path,desc,createtime) VALUES (NULL,?,?,?,?)";
			ps = con.prepareStatement(sql);
			for (DBVersion version : versions) {
				int index = 1;
				ps.setString(index++, version.getVersionNum());
				ps.setString(index++, version.getPath());
				ps.setString(index++, version.getDesc());
				ps.setTimestamp(index++, version.getCreateTime());
				ps.addBatch();
			}
			int[] arr = ps.executeBatch();
			con.commit();
			int k = count(arr);
			return k;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, rs);
		}
	}

	private void upgrade(Connection con) throws Throwable {
		try {
			DBVersion currentVersion = findCurrentVersion(con);
			log.info(currentVersion);

			List notupgradedVersions = findNotupgradedVersion(currentVersion);
			log.info("notupgradedVersions=" + notupgradedVersions);
			if ((notupgradedVersions == null)
					|| (notupgradedVersions.isEmpty())) {
				return;
			}

			DBVersion[] versions = (DBVersion[]) notupgradedVersions
					.toArray(new DBVersion[notupgradedVersions.size()]);
			Arrays.sort(versions);
			log.info("升级数据库:" + Arrays.toString(versions));
			for (DBVersion version : versions) {
				String fileName = "db_schema_upgrade" + version.getVersionNum()
						+ ".sql";
				executeScript(con, fileName);
			}

			addDBVersions(con, versions);
		} catch (Throwable e) {
			log.error("升级数据库出错", e);
			throw e;
		}
	}
}