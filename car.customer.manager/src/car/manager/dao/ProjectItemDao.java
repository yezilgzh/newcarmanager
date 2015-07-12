package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import car.manager.dto.ProjectItem;

public class ProjectItemDao extends CommonDao
{
  public List<ProjectItem> get()
  {
    List items = new ArrayList();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("select id,name,cost,desc from projectitem");
      rs = ps.executeQuery();
      while (rs.next()) {
        ProjectItem item = new ProjectItem();
        item.setName(rs.getString("name"));
        item.setCost(rs.getInt("cost"));
        item.setDesc(rs.getString("desc"));
        item.setId(rs.getString("id"));
        items.add(item);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(con, ps, rs);
    }
    return items;
  }

  public void insert(ProjectItem item)
    throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("insert into projectitem(id, name,cost,desc)values(null,?,?,?)");
      ps.setString(1, item.getName());
      ps.setInt(2, item.getCost());
      ps.setString(3, item.getDesc());
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(con, ps, rs);
    }
  }

  public void update(ProjectItem item) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("update  projectitem set name=?,cost=?,desc=? where id=?");
      ps.setString(1, item.getName());
      ps.setInt(2, item.getCost());
      ps.setString(3, item.getDesc());
      ps.setString(4, item.getId());
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(con, ps, rs);
    }
  }

  public void delete(ProjectItem item) throws Exception
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = getConnection();
      ps = con.prepareStatement("delete from   projectitem   where id=?");
      ps.setString(1, item.getId());
      ps.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      close(con, ps, rs);
    }
  }
}