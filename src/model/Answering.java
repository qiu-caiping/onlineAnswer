package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//留言
@Entity
@Table(name = "t_Answering")
public class Answering implements Serializable {

	private static final long serialVersionUID = -117947798302585032L;

	private int id;//主键
	
     private String mywords;//问答内容(学生)
     
     private String rescontent;//回复(教师)
	
	private Date createDate;
	
	private User user;//关联学生
	
	private User user0;//关联教师
	
	
	
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMywords() {
		return mywords;
	}

	public void setMywords(String mywords) {
		this.mywords = mywords;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRescontent() {
		return rescontent;
	}

	public void setRescontent(String rescontent) {
		this.rescontent = rescontent;
	}

	@ManyToOne
	@JoinColumn(name="user0id")
	public User getUser0() {
		return user0;
	}

	public void setUser0(User user0) {
		this.user0 = user0;
	}
	
	
	
	
	
	
	
}
