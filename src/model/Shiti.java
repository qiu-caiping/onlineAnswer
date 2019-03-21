package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity//试题
@Table(name="t_Shiti")
public class Shiti implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;//主键
	 
	private String wenti;//试题
	
	private String daan;//答案
	
	private Fenlei fenlei;//所属题库
	
	private Date createtime;//添加时间
	
	private int shitilock;//删除状态
	


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

	public int getShitilock() {
		return shitilock;
	}

	public void setShitilock(int shitilock) {
		this.shitilock = shitilock;
	}

	public String getWenti() {
		return wenti;
	}

	public void setWenti(String wenti) {
		this.wenti = wenti;
	}

	public String getDaan() {
		return daan;
	}

	public void setDaan(String daan) {
		this.daan = daan;
	}

	@ManyToOne
	@JoinColumn(name="fenleiid")
	public Fenlei getFenlei() {
		return fenlei;
	}

	public void setFenlei(Fenlei fenlei) {
		this.fenlei = fenlei;
	}

	
	

	
	
	
}
