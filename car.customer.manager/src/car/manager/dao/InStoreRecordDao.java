package car.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import car.manager.dto.Consumer;
import car.manager.dto.InStoreRecord;
import car.manager.util.TimeUtil;

/**
 * 消费记录
 * 
 * @author root
 * 
 */
public class InStoreRecordDao extends CommonDao {
	private ProjectDao projectDao = new ProjectDao();

	public List<InStoreRecord> getTodayRecords() {
		List<InStoreRecord> records = new LinkedList<InStoreRecord>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 1);
			con = getConnection();
			String sql = "select  mr.id,mr.intime, mr.caruserkey,c.carno,c.name,c.cartype,c.color,c.phone,c.remark  from maintainrecord  mr left join  caruser c on mr.caruserkey =c.id  where mr.intime  > ?  ";
			ps = con.prepareStatement(sql);
			ps.setTime(1, new Time(cal.getTimeInMillis()));
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
				projectDao.fillInStoreRecord(con, record);
				records.add(record);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(con, ps, rs);
		}
		return records;

	}

	public void insert(Connection con, InStoreRecord cr) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into maintainrecord(id,intime,remark,caruserkey)values(null,?,?,?)");
			int index = 1;
			ps.setString(index++, TimeUtil.getStandardDate(cr.getTime()));
			ps.setString(index++, cr.getRemark());
			ps.setString(index++, cr.getConsumer().getId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				String key = rs.getString(1);// 主键
				cr.setId(key);
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			close(null, ps, null);
		}

	}

}
