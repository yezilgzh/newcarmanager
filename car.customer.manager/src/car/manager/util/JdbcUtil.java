package car.manager.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;

/**
 * JDBC工具类
 * 
 * @author 钟城 2011-5-9
 * @exclude
 */
public class JdbcUtil {

	/**
	 * 获取日志实例
	 */
	private static Log log = LogFactory.getLog(JdbcUtil.class);
	/**
	 * 数据库连接池
	 */
	private static final JdbcConnectionPool pool;
	static {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("db");
			String url = bundle.getString("jdbc.url");
			String username = bundle.getString("jdbc.username");
			String password = bundle.getString("jdbc.password");
			JdbcDataSource ds = new JdbcDataSource();
			ds.setURL(url);
			ds.setUser(username);
			ds.setPassword(password);
			pool = JdbcConnectionPool.create(ds);
		} catch (RuntimeException e) {
			log.error("创建数据库连接池失败", e);
			throw e;
		}
	}

	/**
	 * 获取Jdbc工具类所产生的连接
	 * 
	 * @throws ClassNotFoundException
	 *             文件未找到异常
	 * @throws SQLException
	 *             数据库异常
	 * @return the con 连接
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (pool.getActiveConnections() > 0) {
			log.info("connection.max=" + pool.getMaxConnections() + ",connection.active=" + pool.getActiveConnections());
		}
		Connection con = pool.getConnection();
		return con;
	}

	/**
	 * 释放连接
	 * 
	 * @param con
	 * @param s
	 * @param rs
	 */
	public static void free(Connection con, Statement s, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Throwable e) {
		}

		try {
			if (s != null) {
				s.close();
			}
		} catch (Throwable e) {
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (Throwable e) {
		}
	}

}
