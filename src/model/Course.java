package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_Course")//课程信息
public class Course implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;
	 
	private String biaoti;//题目
	
	
	private String jianjie;//简介
	

	private String fujian;//文件

	private String fujianYuanshiming;//文件名
	
	private User user;//关联教师
	
	
	private Date createtime;//添加时间
	
	private int courselock;
	


	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}

	
	public int getCourselock() {
		return courselock;
	}

	public void setCourselock(int courselock) {
		this.courselock = courselock;
	}

	public String getFujian() {
		return fujian;
	}

	public void setFujian(String fujian) {
		this.fujian = fujian;
	}

	public String getFujianYuanshiming() {
		return fujianYuanshiming;
	}

	public void setFujianYuanshiming(String fujianYuanshiming) {
		this.fujianYuanshiming = fujianYuanshiming;
	}

	
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
