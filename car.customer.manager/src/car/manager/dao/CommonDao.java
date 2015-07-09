package car.manager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.manager.util.JdbcUtil;

/**
 * 业务处理基础库：数据访问对象基类
 * 
 * @author 钟城
 * @exclude
 */
public abstract class CommonDao {
	/**
	 * 获取日志实例
	 */
	public static final Log log = LogFactory.getLog(CommonDao.class);

	/**
	 * 获取Jdbc工具类产生的连接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected Connection getConnection() throws ClassNotFoundException,
			SQLException {
		return JdbcUtil.getConnection();
	}

	/**
	 * 关闭连接并释放所持有的资源
	 * 
	 * @param con
	 * @param s
	 * @param rs
	 */
	protected void close(Connection con, Statement s, ResultSet rs) {
		JdbcUtil.free(con, s, rs);
	}

}
