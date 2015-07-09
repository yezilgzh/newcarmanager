package car.manager.dto;

/**
 * 消费记录对象
 * 
 * @author root
 * 
 */
public class Consumer {
	private String id;
	// 车牌
	private String carno = "";
	// 车主姓名
	private String name = "";
	// 车型
	private String cartype = "";
	// 颜色
	private String color = "";
	// 手机号
	private String phone = "";
	// 备注
	private String remark = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
