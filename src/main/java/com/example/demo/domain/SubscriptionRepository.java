package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	List<Subscription> findAllByUser_id(int userId);
}
