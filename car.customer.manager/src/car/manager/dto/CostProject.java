package car.manager.dto;

public class CostProject
{
  private String projectName;
  private int cost;
  private String remark;

  public String getProjectName()
  {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public int getCost() {
    return this.cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}