package car.manager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.manager.util.JdbcUtil;

public abstract class CommonDao
{
  public static final Log log = LogFactory.getLog(CommonDao.class);

  protected Connection getConnection()
    throws ClassNotFoundException, SQLException
  {
    return JdbcUtil.getConnection();
  }

  protected void close(Connection con, Statement s, ResultSet rs)
  {
    JdbcUtil.free(con, s, rs);
  }

  protected int count(int[] arr)
  {
    if (arr == null) {
      return 0;
    }
    int result = 0;
    for (int i : arr) {
      result += i;
    }
    return result;
  }
}