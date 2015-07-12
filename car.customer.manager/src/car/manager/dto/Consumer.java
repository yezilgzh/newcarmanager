package car.manager.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Consumer
{
  private String id;
  private String carno = "";

  private String name = "";

  private String cartype = "";

  private String color = "";

  private String phone = "";

  private String remark = "";

  private int sex = 0;
  private Date createTime;
  private List<InStoreRecord> instoreRecords = new LinkedList();

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCarno() {
    return this.carno;
  }

  public void setCarno(String carno) {
    this.carno = carno;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCartype() {
    return this.cartype;
  }

  public void setCartype(String cartype) {
    this.cartype = cartype;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<InStoreRecord> getInstoreRecords() {
    return this.instoreRecords;
  }

  public void setInstoreRecords(List<InStoreRecord> instoreRecords) {
    this.instoreRecords = instoreRecords;
  }

  public int getSex() {
    return this.sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}