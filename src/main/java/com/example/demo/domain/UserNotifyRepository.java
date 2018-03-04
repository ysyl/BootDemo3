package com.example.demo.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotifyRepository extends JpaRepository<UserNotify, Long> {
	List<UserNotify> findAllByNotify_typeOrderByCreateAt (NoticeType type);
	
	List<UserNotify> findAllByNotify_typeAndUser(NoticeType type, User user);
}
