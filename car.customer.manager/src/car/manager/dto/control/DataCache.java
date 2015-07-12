package car.manager.dto.control;

import java.util.List;

import car.manager.dao.ColorDao;
import car.manager.dao.ProjectItemDao;
import car.manager.dao.RecordService;
import car.manager.dto.InStoreRecord;

public class DataCache
{
  private static DataCache instance = new DataCache();

  private RecordService service = new RecordService();
  private ColorDao colorDao = new ColorDao();
  private ProjectItemDao projectItemDao = new ProjectItemDao();

  public static DataCache getInstance() {
    return instance;
  }

  public RecordService getService() {
    return this.service;
  }

  public List<InStoreRecord> getRecords() {
    return this.service.getTodayRecords();
  }

  public ColorDao getColorDao() {
    return this.colorDao;
  }

  public ProjectItemDao getProjectItemDao() {
    return this.projectItemDao;
  }
}