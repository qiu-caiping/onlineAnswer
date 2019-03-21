package model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//学院
@Entity
@Table(name="t_College")
public class College {
	
	@Id
	@GeneratedValue
	private int id;//主键
	
	private String name;//学院名

	private Date createtime;//添加时间

	private int collegelock;// 删除状态 0表示未删除 1表示已删除
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getCollegelock() {
		return collegelock;
	}

	public void setCollegelock(int collegelock) {
		this.collegelock = collegelock;
	}

	
}
