package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity//测试记录表
@Table(name="t_Kaoshijilu")
public class Kaoshijilu implements Serializable{

	private static final long serialVersionUID = -7141419035239709511L;

	private long id;//主键
	
	private Kaoshi kaoshi;//关联测试
	
	private String wodedaan;//我的答案
	
	private Date createtime;//添加时间
	
	private Shiti shiti;//关联试题
	
	private double defen;//得分
	

	
	
	
	
	@ManyToOne
	@JoinColumn(name="kaoshiid")
	public Kaoshi getKaoshi() {
		return kaoshi;
	}

	public void setKaoshi(Kaoshi kaoshi) {
		this.kaoshi = kaoshi;
	}

	public double getDefen() {
		return defen;
	}

	public void setDefen(double defen) {
		this.defen = defen;
	}

	@ManyToOne
	@JoinColumn(name="shitiid")
	public Shiti getShiti() {
		return shiti;
	}

	public void setShiti(Shiti shiti) {
		this.shiti = shiti;
	}

	

	public String getWodedaan() {
		return wodedaan;
	}

	public void setWodedaan(String wodedaan) {
		this.wodedaan = wodedaan;
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
