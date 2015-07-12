package car.manager.dto;

public class ProjectItem
{
  private String id;
  private String name;
  private int cost;
  private String desc;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCost() {
    return this.cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
}