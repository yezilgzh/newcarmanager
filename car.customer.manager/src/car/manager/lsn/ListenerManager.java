package car.manager.lsn;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerManager
{
  public static final Map<String, RefreshListener> refreshs = new ConcurrentHashMap();

  public static void addListener(String key, RefreshListener lsn) {
    refreshs.put(key, lsn);
  }

  public static void removeListener(String key) {
    refreshs.remove(key);
  }

  public static void fireListener(String key) {
    RefreshListener lsn = (RefreshListener)refreshs.get(key);
    if (lsn != null)
      lsn.refresh();
  }
}