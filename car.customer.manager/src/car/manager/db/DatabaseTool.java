package car.manager.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class DatabaseTool
{
  private static ResourceBundle bundle = ResourceBundle.getBundle("db");

  private static String getString(String key, String defaultValue) {
    try {
      return bundle.getString(key).trim(); } catch (Throwable e) {
    }
    return defaultValue;
  }

  public static void installAndUpgradeByH2(File jarFile)
    throws SQLException, ClassNotFoundException
  {
    installAndUpgrade(jarFile, getDBConnect());
  }

  public static void installAndUpgrade(File jarFile, Connection con)
  {
    if ((jarFile == null) || (con == null)) {
      throw new NullPointerException();
    }
    new JarDBInitializer(jarFile.getAbsolutePath()).installAndUpgrade(con);
  }

  private static Connection getDBConnect()
  {
    Connection con = null;
    try {
      String driver = getString("jdbc.driver", "org.h2.Driver");
      String url = getString("jdbc.url.init", "jdbc:h2:file:h2/hedge-db");
      String username = getString("jdbc.username", "root");
      String password = getString("jdbc.password", "");
      Class.forName(driver);
      con = DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      throw new RuntimeException("获取数据库连接失败", e);
    }
    return con;
  }
}