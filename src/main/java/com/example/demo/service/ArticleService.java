package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleRepository;
import com.example.demo.domain.UserRepository;

@Service
public class ArticleService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	
	public void postArticle(Article article, int userId)
	{
		article.setUser(userRepository.findById(userId));	
		articleRepository.save(article);
	}
	
	public List<Article> findArticleByUser(int id)
	{
		return articleRepository.findByUser_id(id);
	}
	
	public Long countLikedUser(Article article) {
		return userRepository.countByLikedArticles(article);
	}
	
}
