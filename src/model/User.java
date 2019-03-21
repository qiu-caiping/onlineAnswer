package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity//用户表
@Table(name="t_User")
public class User implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;//主键
	
	private String username;//用户名
	
	private String password;//密码
	
	private String truename;//姓名
	
	private College college;//学院
	
	private String telephone;//手机号
	
	private String xingbie;//性别
	
	private String age;//年龄
	
	private Date createtime;//添加时间

	private int role;//用户：1表示管理员.2表示学生  3表示教师
	
	private int userlock;//删除状态

	

	public int getUserlock() {
		return userlock;
	}

	public void setUserlock(int userlock) {
		this.userlock = userlock;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getXingbie() {
		return xingbie;
	}

	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@ManyToOne
	@JoinColumn(name="collegeid")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	
	
}
