package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
UserNotify用于关联用户与消息，存放消息是否读取等消息
*/

@Entity
@Table(name = "user_notify")
public class UserNotify {
	
	@Id
	@Column(name="id")
	@GeneratedValue 
	private Long id;

	//关联用户
	@ManyToOne
	private User user;

	//关联Notice
	@ManyToOne
	private Notice notify;
	
	//订阅时间

	private Date createAt;
	
	protected UserNotify() {};
	
	
	
	public UserNotify(Notice notify, Date createAt) {
		super();
		this.notify = notify;
		this.createAt = createAt;
	}



	public UserNotify(User user, Notice notify, Date createAt) {
		super();
		this.user = user;
		this.notify = notify;
		this.createAt = createAt;
	}



	private boolean isRead = false;
	
	

	public boolean isRead() {
		return isRead;
	}

	public User getUser() {
		return user;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Notice getNotify() {
		return notify;
	}

	public Date getCreateAt() {
		return createAt;
	}	
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setNotify(Notice notify) {
		this.notify = notify;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
}
