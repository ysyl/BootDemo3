package com.example.demo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Notice")
public class Notice {
	
	public Notice() {
		
	}
	
	//创建公告和信息
	public Notice(NoticeType type, String content, User sender,Date createAt) {
		super();
		this.type = type;
		this.content = content;
		this.sender = sender;
		this.createAt = createAt;
	}
	//创建提醒 remind
	public Notice(NoticeType type, Long target, NoticeTargetType targetType,  NoticeActionType action, User sender,
			Date createAt) {
		super();
		this.type = type;
		this.targetType = targetType;
		this.target = target;
		this.action = action;
		this.sender = sender;
		this.createAt = createAt;
	}

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private int id;
		
	@Column( nullable = false)
	private NoticeType type;
	
	//消息内容，用于公告和提醒
	private String content;
	
	// 目标的类型 小明（发送者）订阅 （动作）了某文章（目标及目标类型）
	private NoticeTargetType targetType;
	
	//目标ID
	private Long target;
	
	//动作类型
	private NoticeActionType action;
	
	//发送者
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	
	
	public int getId() {
		return id;
	}

	public NoticeType getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public NoticeTargetType getTargetType() {
		return targetType;
	}

	public Long getTarget() {
		return target;
	}

	public NoticeActionType getAction() {
		return action;
	}

	public User getSender() {
		return sender;
	}

	public Date getCreateAt() {
		return createAt;
	}

	private Date createAt;
}
