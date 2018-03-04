package com.example.demo.domain;

import java.io.Serializable;

public class SubscriptionId implements Serializable {
	private Long target;
	private NoticeTargetType targetType;
	private NoticeActionType action;
	
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
}
