package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Comment findById(Long id);
	List<Comment> findByArticle_id(Long id);
	List<Comment> findByUser_id(int id);
	Comment save(Comment comment);
}
