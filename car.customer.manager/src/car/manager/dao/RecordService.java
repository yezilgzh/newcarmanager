package car.manager.dao;

import java.sql.Connection;
import java.util.List;

import car.manager.dto.Consumer;
import car.manager.dto.InStoreRecord;
import car.manager.util.JdbcUtil;

public class RecordService
{
  private ConsumerDao consumerDao = new ConsumerDao();
  private InStoreRecordDao instoreRecordDao = new InStoreRecordDao();
  private ProjectDao projectDao = new ProjectDao();

  public List<InStoreRecord> getTodayRecords()
  {
    List records = this.instoreRecordDao.getTodayRecords();
    return records;
  }

  public void insert(InStoreRecord ir)
  {
    Connection con = null;
    try {
      con = JdbcUtil.getConnection();
      con.setAutoCommit(false);
      if (ir.getConsumer().getId() == null)
        this.consumerDao.insert(con, ir.getConsumer());
      this.instoreRecordDao.insert(con, ir);
      this.projectDao.insert(con, ir);
      con.commit();
      con.setAutoCommit(true);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.free(con, null, null);
    }
  }

  public void updateRecord(InStoreRecord ir) throws Exception
  {
    Connection con = null;
    try {
      con = JdbcUtil.getConnection();
      con.setAutoCommit(false);
      this.consumerDao.update(con, ir.getConsumer(), ir.getConsumer());
      this.projectDao.delete(con, ir);
      this.projectDao.insert(con, ir);
      con.commit();
      con.setAutoCommit(true);
    } catch (Exception e) {
      throw e;
    } finally {
      JdbcUtil.free(con, null, null);
    }
  }

  public Consumer getConsumerWithInStoreRecords(String carno)
  {
    Connection con = null;
    Consumer consumer = null;
    try {
      con = JdbcUtil.getConnection();
      con.setAutoCommit(false);
      consumer = this.consumerDao.get(con, carno);
      this.instoreRecordDao.fillConsumer(con, consumer);
      this.projectDao.fillConsumer(con, consumer);
      con.commit();
      con.setAutoCommit(true);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.free(con, null, null);
    }
    return consumer;
  }

  public ConsumerDao getConsumerDao()
  {
    return this.consumerDao;
  }

  public InStoreRecordDao getInStoreRecordDao() {
    return this.instoreRecordDao;
  }

  public ProjectDao getProjectDao() {
    return this.projectDao;
  }
}