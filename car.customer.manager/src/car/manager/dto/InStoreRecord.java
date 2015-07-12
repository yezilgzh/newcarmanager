package car.manager.dto;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InStoreRecord
{
  private String id;
  private Date time = new Date();
  private Consumer consumer;
  private String remark;
  private List<CostProject> costProjects = new LinkedList();

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTime() {
    return this.time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public List<CostProject> getCostProjects() {
    return this.costProjects;
  }

  public void setCostProjects(List<CostProject> costProjects) {
    this.costProjects = costProjects;
  }

  public Consumer getConsumer() {
    return this.consumer;
  }

  public void setConsumer(Consumer consumer) {
    this.consumer = consumer;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public int getCost() {
    int cost = 0;
    for (CostProject cp : this.costProjects) {
      cost += cp.getCost();
    }
    return cost;
  }

  public String getProjects()
  {
    StringBuilder sb = new StringBuilder();
    Iterator it = this.costProjects.iterator();
    while (it.hasNext()) {
      sb.append(((CostProject)it.next()).getProjectName());
      if (it.hasNext()) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
}