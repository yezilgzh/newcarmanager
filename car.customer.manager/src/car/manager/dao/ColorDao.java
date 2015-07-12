package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColorDao extends CommonDao
{
  public String[] get()
  {
    List colors = new ArrayList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("select color from color");
      rs = ps.executeQuery();
      while (rs.next()) {
        String color = rs.getString("color");
        colors.add(color);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(con, ps, rs);
    }
    return (String[])colors.toArray(new String[0]);
  }

  public void delete(String color) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("delete from  color c where c.color=? ");
      ps.setString(1, color);
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(con, ps, rs);
    }
  }

  public void insert(String color)
    throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("insert into color(id,color)values(null,?) ");
      ps.setString(1, color);
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(con, ps, rs);
    }
  }
}