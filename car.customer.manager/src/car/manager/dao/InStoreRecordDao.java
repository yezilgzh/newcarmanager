package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import car.manager.dto.Consumer;
import car.manager.dto.InStoreRecord;
import car.manager.util.TimeUtil;

public class InStoreRecordDao extends CommonDao
{
  private ProjectDao projectDao = new ProjectDao();

  public List<InStoreRecord> getRecordsByDate(Date start, Date end) {
    List records = new LinkedList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      Calendar startCal = Calendar.getInstance();
      startCal.setTime(start);
      startCal.set(11, 0);
      startCal.set(12, 0);
      startCal.set(13, 0);
      startCal.set(14, 1);
      Calendar endCal = Calendar.getInstance();
      endCal.setTime(end);
      endCal.set(11, 23);
      endCal.set(12, 59);
      endCal.set(13, 59);
      endCal.set(14, 599);

      con = getConnection();
      String sql = "select  mr.id,mr.intime, mr.caruserkey,c.carno,c.name,c.cartype,c.color,c.phone,c.remark  from instorerecord  mr left join  caruser c on mr.caruserkey =c.id  where mr.intime  between ? and  ?  ";
      ps = con.prepareStatement(sql);
      ps.setTimestamp(1, new Timestamp(startCal.getTime().getTime()));
      ps.setTimestamp(2, new Timestamp(endCal.getTime().getTime()));
      rs = ps.executeQuery();
      while (rs.next()) {
        InStoreRecord record = new InStoreRecord();
        String id = rs.getString("id");
        record.setId(id);
        Timestamp intime = rs.getTimestamp("intime");
        record.setTime(new Date(intime.getTime()));
        Consumer cosumer = new Consumer();
        String cid = rs.getString("caruserkey");
        cosumer.setId(cid);
        String carno = rs.getString("carno");
        cosumer.setCarno(carno);
        String name = rs.getString("name");
        cosumer.setName(name);
        String cartype = rs.getString("cartype");
        cosumer.setCartype(cartype);
        String color = rs.getString("color");
        cosumer.setColor(color);
        String phone = rs.getString("phone");
        cosumer.setPhone(phone);
        String remark = rs.getString("remark");
        cosumer.setRemark(remark);
        record.setConsumer(cosumer);
        this.projectDao.fillInStoreRecord(con, record);
        records.add(record);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(con, ps, rs);
    }
    return records;
  }

  public List<InStoreRecord> getTodayRecords()
  {
    List records = new LinkedList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HHmmss.SSS");
      Calendar cal = Calendar.getInstance();
      cal.set(11, 0);
      cal.set(12, 0);
      cal.set(13, 0);
      cal.set(14, 1);
      con = getConnection();
      String sql = "select  mr.id,mr.intime, mr.caruserkey,c.carno,c.name,c.cartype,c.color,c.phone,c.remark  from instorerecord  mr left join  caruser c on mr.caruserkey =c.id  where mr.intime  > ?  ";
      ps = con.prepareStatement(sql);
      ps.setTimestamp(1, new Timestamp(cal.getTime().getTime()));
      rs = ps.executeQuery();

      while (rs.next()) {
        InStoreRecord record = new InStoreRecord();
        String id = rs.getString("id");
        record.setId(id);
        Timestamp intime = rs.getTimestamp("intime");
        record.setTime(new Date(intime.getTime()));
        Consumer cosumer = new Consumer();
        String cid = rs.getString("caruserkey");
        cosumer.setId(cid);
        String carno = rs.getString("carno");
        cosumer.setCarno(carno);
        String name = rs.getString("name");
        cosumer.setName(name);
        String cartype = rs.getString("cartype");
        cosumer.setCartype(cartype);
        String color = rs.getString("color");
        cosumer.setColor(color);
        String phone = rs.getString("phone");
        cosumer.setPhone(phone);
        String remark = rs.getString("remark");
        cosumer.setRemark(remark);
        record.setConsumer(cosumer);
        this.projectDao.fillInStoreRecord(con, record);
        records.add(record);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(con, ps, rs);
    }
    return records;
  }

  public void insert(Connection con, InStoreRecord cr)
  {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("insert into instorerecord(id,intime,remark,caruserkey)values(null,?,?,?)");
      int index = 1;
      ps.setString(index++, TimeUtil.getStandardDate(cr.getTime()));
      ps.setString(index++, cr.getRemark());
      ps.setString(index++, cr.getConsumer().getId());
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        String key = rs.getString(1);
        cr.setId(key);
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    } finally {
      close(null, ps, null);
    }
  }

  public void delete(Connection con, InStoreRecord cr)
  {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("delete  from  instorerecord where id=?");
      int index = 1;
      ps.setString(index++, cr.getId());
      ps.executeUpdate();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    } finally {
      close(null, ps, null);
    }
  }

  public void fillConsumer(Connection con, Consumer consumer)
  {
    List recoreds = new LinkedList();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement("select id,intime,remark from  instorerecord where caruserkey=?");
      int index = 1;
      ps.setString(index++, consumer.getId());
      rs = ps.executeQuery();
      while (rs.next()) {
        InStoreRecord record = new InStoreRecord();
        String id = rs.getString("id");
        record.setId(id);
        Timestamp intime = rs.getTimestamp("intime");
        record.setTime(new Date(intime.getTime()));
        recoreds.add(record);
      }
      consumer.setInstoreRecords(recoreds);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    } finally {
      close(null, ps, null);
    }
  }
}