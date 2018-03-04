package com.example.demo.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	List<Notice> findAllByTargetAndTargetTypeAndActionAndCreateAtAfter(Long target, 
															NoticeTargetType targetType,
															NoticeActionType action,
															Date lastTime);
	
	List<Notice> findAllByTypeAndSenderAndCreateAtAfter(NoticeType targetType, User sender, Date lastTime);
	
	List<Notice> findAllByTypeAndCreateAtAfter(NoticeType noticeType, Date lastTime);
	
	Notice save(Notice notice);
}
