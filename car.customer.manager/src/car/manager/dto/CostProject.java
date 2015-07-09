package car.manager.dto;

/**
 * 消费项目
 * 
 * @author root
 * 
 */
public class CostProject {

	private String projectName;
	private int cost;
	private String remark;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
