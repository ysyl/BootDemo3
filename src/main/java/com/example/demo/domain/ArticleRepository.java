package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	Article findById(Long id);
	List<Article> findAll();
	List<Article> findByUser_id(int id);	
	Article save(com.example.demo.domain.Article article);
	List<Article> findAllByLikedUsers(User user);
	
	@Query("from User as user left join fetch user.likedArticles where user.id = :id")
	List<Article> findAllLikedArticleByUserId(@Param("id") int userId);
}
