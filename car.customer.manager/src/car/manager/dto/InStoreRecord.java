package car.manager.dto;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 消费记录
 * 
 * @author root
 * 
 */
public class InStoreRecord {

	private String id;
	// 进店时间
	private Date time = new Date();
	// 消费者
	private Consumer consumer;
	private String remark;
	private LinkedList<CostProject> costProjects = new LinkedList<CostProject>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public LinkedList<CostProject> getCostProjects() {
		return costProjects;
	}

	public void setCostProjects(LinkedList<CostProject> costProjects) {
		this.costProjects = costProjects;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCost() {
		int cost = 0;
		for (CostProject cp : costProjects) {
			cost = cost + cp.getCost();
		}
		return cost;

	}

	public String getProjects() {
		StringBuilder sb = new StringBuilder();
		Iterator<CostProject> it = costProjects.iterator();
		while (it.hasNext()) {
			sb.append(it.next().getProjectName());
			if (it.hasNext())
				sb.append(",");

		}
		return sb.toString();

	}

}
