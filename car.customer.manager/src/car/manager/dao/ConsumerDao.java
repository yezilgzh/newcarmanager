package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import car.manager.dto.Consumer;

public class ConsumerDao extends CommonDao
{
  public List<Consumer> get()
  {
    LinkedList css = new LinkedList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();

      ps = con.prepareStatement("select id,carno,name,cartype,color,phone,remark ,sex,createtime  from caruser  ");
      rs = ps.executeQuery();
      while (rs.next()) {
        Consumer cs = new Consumer();
        String id = rs.getString("id");
        cs.setId(id);
        String dbcarno = rs.getString("carno");
        cs.setCarno(dbcarno);
        String name = rs.getString("name");
        cs.setName(name);
        String cartype = rs.getString("cartype");
        cs.setCartype(cartype);
        String color = rs.getString("color");
        cs.setColor(color);
        String phone = rs.getString("phone");
        cs.setPhone(phone);
        String remark = rs.getString("remark");
        cs.setRemark(remark);
        int sex = rs.getInt("sex");
        cs.setSex(sex);
        Timestamp time = rs.getTimestamp("createtime");
        Date d = new Date(time.getTime());
        cs.setCreateTime(d);
        css.add(cs);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(con, ps, rs);
    }
    return css;
  }

  public List<Consumer> getFuzzyConsumer(String fcarnoOrPhoneNum, boolean type)
  {
    LinkedList css = new LinkedList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      fcarnoOrPhoneNum = "'%" + fcarnoOrPhoneNum + "%'";
      String sql = "select id,carno,name,cartype,color,phone,remark ,sex from caruser ";
      if (type)
        sql = sql + "where carno like " + fcarnoOrPhoneNum;
      else {
        sql = sql + "where   phone like " + fcarnoOrPhoneNum;
      }
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        Consumer cs = new Consumer();
        String id = rs.getString("id");
        cs.setId(id);
        String dbcarno = rs.getString("carno");
        cs.setCarno(dbcarno);
        String name = rs.getString("name");
        cs.setName(name);
        String cartype = rs.getString("cartype");
        cs.setCartype(cartype);
        String color = rs.getString("color");
        cs.setColor(color);
        String phone = rs.getString("phone");
        cs.setPhone(phone);
        String remark = rs.getString("remark");
        cs.setRemark(remark);
        int sex = rs.getInt("sex");
        cs.setSex(sex);
        css.add(cs);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(con, ps, rs);
    }
    return css;
  }

  public Consumer get(String carno) {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("select id,carno,name,cartype,color,phone,remark,sex from caruser where carno=? ");
      ps.setString(1, carno);
      rs = ps.executeQuery();
      if (rs.next()) {
        Consumer cs = new Consumer();
        String id = rs.getString("id");
        cs.setId(id);
        String dbcarno = rs.getString("carno");
        cs.setCarno(dbcarno);
        String name = rs.getString("name");
        cs.setName(name);
        String cartype = rs.getString("cartype");
        cs.setCartype(cartype);
        String color = rs.getString("color");
        cs.setColor(color);
        String phone = rs.getString("phone");
        cs.setPhone(phone);
        String remark = rs.getString("remark");
        cs.setRemark(remark);
        int sex = rs.getInt("sex");
        cs.setSex(sex);
        Consumer localConsumer1 = cs;
        return localConsumer1;
      }
    } catch (Exception e) {
      throw new RuntimeException("");
    } finally {
      close(con, ps, rs); } close(con, ps, rs);

    return null;
  }

  public Consumer get(Connection con, String carno) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement("select id,carno,name,cartype,color,phone,remark,sex from caruser where carno=? ");
      ps.setString(1, carno);
      rs = ps.executeQuery();
      if (rs.next()) {
        Consumer cs = new Consumer();
        String id = rs.getString("id");
        cs.setId(id);
        String dbcarno = rs.getString("carno");
        cs.setCarno(dbcarno);
        String name = rs.getString("name");
        cs.setName(name);
        String cartype = rs.getString("cartype");
        cs.setCartype(cartype);
        String color = rs.getString("color");
        cs.setColor(color);
        String phone = rs.getString("phone");
        cs.setPhone(phone);
        String remark = rs.getString("remark");
        cs.setRemark(remark);
        int sex = rs.getInt("sex");
        cs.setSex(sex);
        Consumer localConsumer1 = cs;
        return localConsumer1;
      }
    } catch (Exception e) {
      throw new RuntimeException("");
    } finally {
      close(null, ps, rs); } close(null, ps, rs);

    return null;
  }

  public void insert(Connection con, Consumer ccr) {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("insert into caruser(id,carno,name,cartype,color,phone,remark,sex,createtime)values(null,?,?,?,?,?,?,?,?)");
      int index = 1;
      ps.setString(index++, ccr.getCarno());
      ps.setString(index++, ccr.getName());
      ps.setString(index++, ccr.getCartype());
      ps.setString(index++, ccr.getColor());
      ps.setString(index++, ccr.getPhone());
      ps.setString(index++, ccr.getRemark());
      ps.setInt(index++, ccr.getSex());
      Date d = new Date();
      ps.setTimestamp(index++, new Timestamp(d.getTime()));
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        String key = rs.getString(1);
        ccr.setId(key);
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    } finally {
      close(null, ps, null);
    }
  }

  public void update(Consumer cs) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("update caruser set carno=?,name=?,cartype=?,color=?,phone=?,remark=?,sex=? where id=?");
      int index = 1;
      ps.setString(index++, cs.getCarno());
      ps.setString(index++, cs.getName());
      ps.setString(index++, cs.getCartype());
      ps.setString(index++, cs.getColor());
      ps.setString(index++, cs.getPhone());
      ps.setString(index++, cs.getRemark());
      ps.setInt(index++, cs.getSex());
      ps.setString(index++, cs.getId());
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(con, ps, null);
    }
  }

  public void update(Connection con, Consumer cs, Consumer ncs) throws Exception
  {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("update caruser set carno=?,name=?,cartype=?,color=?,phone=?,remark=?,sex=? where id=?");
      int index = 1;
      ps.setString(index++, ncs.getCarno());
      ps.setString(index++, ncs.getName());
      ps.setString(index++, ncs.getCartype());
      ps.setString(index++, ncs.getColor());
      ps.setString(index++, ncs.getPhone());
      ps.setString(index++, ncs.getRemark());
      ps.setInt(index++, ncs.getSex());
      ps.setString(index++, cs.getId());
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(null, ps, null);
    }
  }
}