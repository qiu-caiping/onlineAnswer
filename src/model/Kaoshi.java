package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity//测试表
@Table(name="t_Kaoshi")
public class Kaoshi implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;//主键
	
	private double fenshu;//测试分数
	
	private User user;//用户名
	
	private Date createtime;//添加时间
	
	private int kaoshilock;//删除状态
	
	
	

	public double getFenshu() {
		return fenshu;
	}

	public void setFenshu(double fenshu) {
		this.fenshu = fenshu;
	}

	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getKaoshilock() {
		return kaoshilock;
	}

	public void setKaoshilock(int kaoshilock) {
		this.kaoshilock = kaoshilock;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
