package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//uniqueConstraints = {@UniqueConstraint(columnNames= {"target", "targetType", "action", "user"})}

@Entity
@Table(name = "Subscription")
@IdClass(SubscriptionId.class)
public class Subscription implements Serializable {
		
	public Subscription() {};
	
	public Subscription(Long target, NoticeTargetType targetType, NoticeActionType action, User user, Date createAt) {
		super();
		this.target = target;
		this.targetType = targetType;
		this.action = action;
		this.user = user;
		this.createAt = createAt;
	}

	@Id
	@Column(nullable = false)
	private Long target;
	
	@Id
	@Column(nullable = false)
	private NoticeTargetType targetType;
	
	@Id
	@Column(nullable = false)
	private NoticeActionType action;
	
	@ManyToOne
	private User user;
	
	//订阅时间，早于该时间的Notice不进行抓取
	private Date createAt;

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTarget() {
		return target;
	}

	public void setTarget(Long target) {
		this.target = target;
	}

	public NoticeTargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(NoticeTargetType targetType) {
		this.targetType = targetType;
	}

	public NoticeActionType getAction() {
		return action;
	}

	public void setAction(NoticeActionType action) {
		this.action = action;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public User getUser() {
		return user;
	}
}
