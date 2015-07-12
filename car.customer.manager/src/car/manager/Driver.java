package car.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dfitc.studio.util.DatabaseTool;

public class Driver
{
  private static Log log = LogFactory.getLog(Driver.class);
  private static double file_version = 1.1D;
  private static double tsdb_version = 1.2D;
  private static boolean loaded;

  public static void load(Class<?> clazz)
    throws Throwable
  {
    if (loaded)
      return;
    try
    {
      String jarName = "/stp-studio-sim.jar";
      String userDir = System.getProperty("user.dir");
      String path = userDir + "/jartmp";

      if (path.getBytes().length != path.length()) {
        log.error("非法安装路径:" + path);
        return;
      }

      File file = new File(path + jarName);
      file.getParentFile().mkdirs();
      if (file.exists())
        file.createNewFile();
      streamToFile(clazz.getResourceAsStream("/lib" + jarName), file);
      DatabaseTool.installAndUpgradeByH2(file);
    } catch (Throwable e) {
      throw e;
    }

    loaded = true;
  }

  private static void streamToFile(InputStream is, File destFile) throws IOException
  {
    copyStream(is, new FileOutputStream(destFile));
  }

  private static void copyStream(InputStream is, OutputStream os) throws IOException
  {
    byte[] bytes = new byte[1024];
    int len = 0;
    while ((len = is.read(bytes)) > 0) {
      os.write(bytes, 0, len);
    }
    is.close();
    os.close();
  }
}