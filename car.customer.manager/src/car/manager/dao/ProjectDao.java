package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import car.manager.dto.CostProject;
import car.manager.dto.InStoreRecord;

public class ProjectDao extends CommonDao {
	/**
	 * 
	 * @param con
	 * @param ir
	 */
	public void fillInStoreRecord(Connection con, InStoreRecord ir) {
		LinkedList<CostProject> costProjects = new LinkedList<CostProject>();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("select  projectname,projectcost,remark,incarkey from maintainproject where id=?");
			int index = 1;
			ps.setString(index++, ir.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CostProject cp = new CostProject();
				String name = rs.getString("projectname");
				cp.setProjectName(name);
				int projectcost = rs.getInt("projectcost");
				cp.setCost(projectcost);
				String remark = rs.getString("remark");
				cp.setRemark(remark);
				costProjects.add(cp);
			}
			ir.setCostProjects(costProjects);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, null);
		}

	}

	public void insert(Connection con, InStoreRecord cr,
			LinkedList<CostProject> cps) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into maintainproject(id,projectname,projectcost,remark,incarkey)values(null,?,?,?,?)");

			int index = 1;
			for (CostProject cp : cps) {
				index = 1;
				ps.setString(index++, cp.getProjectName());
				ps.setString(index++, String.valueOf(cp.getCost()));
				ps.setString(index++, cp.getRemark());
				ps.setString(index++, cr.getId());
				ps.addBatch();

			}
			ps.executeBatch();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, null);
		}

	}
}
