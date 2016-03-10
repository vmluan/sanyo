package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/*
 * craete by DuongPhan 
 * use save update price notification for user
 * */
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "notification", catalog = "sanyo")
public class Notification implements java.io.Serializable{
	private Integer notificationId;
	private String define; //loại định nghĩa cho thông báo (update_price for price update)
	private String contents; //content notification
	private User userid;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NOTIFICATION_ID", unique = true, nullable = false)
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid", nullable = true)
	public User getUserid() {
		return userid;
	}
	public void setUserid(User userid) {
		this.userid = userid;
	}
	@NotEmpty
	@Column(name = "DEFINE", nullable = false, length = 100)
	public String getDefine() {
		return define;
	}
	public void setDefine(String define) {
		this.define = define;
	}
	@Column(name = "CONTENTS")
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

}
