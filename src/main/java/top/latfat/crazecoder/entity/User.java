package top.latfat.crazecoder.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 用户
 * @author xiaochen@inshn
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="user", catalog="blog")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements java.io.Serializable {

	private String id;
	private String name;
	private String pwd;
	public User() {
	}
	public User(String pwd, String name) {
		this.name = name;
		this.pwd = pwd;
	}
	@Id
	@Column(name="id", unique=true, length=50)
	public String getId() {
		if (StringUtils.isBlank(this.id)) {
			this.id = UUID.randomUUID().toString();
		}
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="name", unique=true, nullable=false, length=30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="pwd", nullable=false, length=30)
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "id=" + id + "\nname=" + name + "\npwd=" + pwd + "\n";
	}
}
