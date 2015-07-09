package car.manager.dao;

import java.sql.Connection;
import java.util.List;

import car.manager.dto.InStoreRecord;
import car.manager.util.JdbcUtil;

public class RecordService {
	private ConsumerDao ccrDao = new ConsumerDao();
	private InStoreRecordDao crDao = new InStoreRecordDao();
	private ProjectDao projectDao = new ProjectDao();

	/**
	 * 获取当天的记录
	 * 
	 * @return
	 */
	public List<InStoreRecord> getTodayRecords() {
		List<InStoreRecord> records = crDao.getTodayRecords();

		return records;
	}

	/**
	 * 记录插入数据库
	 * 
	 * @param ccr
	 */
	public void insert(InStoreRecord ir) {
		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
			con.setAutoCommit(false);
			if (ir.getConsumer().getId() == null)
				ccrDao.insert(con, ir.getConsumer());
			crDao.insert(con, ir);
			projectDao.insert(con, ir, ir.getCostProjects());
			con.commit();
			con.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(con, null, null);
		}

	}

}
