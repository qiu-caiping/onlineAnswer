package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity//题库
@Table(name="t_Fenlei")
public class Fenlei implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;//主键
	 
	private String name;//题库名
	
	private User user;//教师
	
	private Date createtime;//添加时间
	
	private int fenleilock;//删除状态
	


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFenleilock() {
		return fenleilock;
	}

	public void setFenleilock(int fenleilock) {
		this.fenleilock = fenleilock;
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
