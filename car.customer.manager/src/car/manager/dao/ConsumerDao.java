package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import car.manager.dto.Consumer;
import car.manager.util.TimeUtil;

/**
 * 消费记录DAO
 * 
 * @author root
 * 
 */
public class ConsumerDao extends CommonDao {

	public Consumer get(String carno) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			ps = con.prepareStatement("select id,carno,name,cartype,color,phone,remark from caruser where carno=? ");
			ps.setString(1, carno);
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
				return cs;
			}
		} catch (Exception e) {
			throw new RuntimeException("");
		} finally {
			close(con, ps, rs);
		}
		return null;
	}

	public void insert(Connection con, Consumer ccr) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into caruser(id,carno,name,cartype,color,phone,remark)values(null,?,?,?,?,?,?)");
			int index = 1;
			ps.setString(index++, ccr.getCarno());
			ps.setString(index++, ccr.getName());
			ps.setString(index++, ccr.getCartype());
			ps.setString(index++, ccr.getColor());
			ps.setString(index++, ccr.getPhone());
			ps.setString(index++, ccr.getRemark());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				String key = rs.getString(1);// 主键
				ccr.setId(key);
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, null);
		}

	}

}
