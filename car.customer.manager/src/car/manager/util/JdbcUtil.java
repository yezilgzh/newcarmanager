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

public class JdbcUtil
{
  private static Log log = LogFactory.getLog(JdbcUtil.class);
  private static final JdbcConnectionPool pool;

  static
  {
    try
    {
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

  public static Connection getConnection()
    throws ClassNotFoundException, SQLException
  {
    if (pool.getActiveConnections() > 0) {
      log.info("connection.max=" + pool.getMaxConnections() + ",connection.active=" + pool.getActiveConnections());
    }
    Connection con = pool.getConnection();
    return con;
  }

  public static void free(Connection con, Statement s, ResultSet rs)
  {
    try
    {
      if (rs != null)
        rs.close();
    }
    catch (Throwable localThrowable)
    {
    }
    try {
      if (s != null)
        s.close();
    }
    catch (Throwable localThrowable1)
    {
    }
    try {
      if (con != null)
        con.close();
    }
    catch (Throwable localThrowable2)
    {
    }
  }
}