package com.example.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService	userService;
	
	@PostConstruct
	void init()
	{
	}
	public List<User> findAll()
	{
		return userRepository.findAll();
	}
	
	public User findUser(String name)
	{
		return userRepository.findByName(name);
	}
	public User findUser(int id)
	{
		return userRepository.findById(id);
	}
	public void save(User user)
	{
		userRepository.save(user);
	}
	
	public List<Article> findAllArticle()	
	{
		return articleRepository.findAll();
	}
	
	public List<Article> findArticleByUserId(int idd)
	{
		return articleRepository.findByUser_id(idd);
	}
	
	public List<Article> getLikedArticles(User user) {
		return articleRepository.findAllByLikedUsers(user);
	}
	
	public boolean isLikedThisArticle(User user, Article article) {
		List<Article> likedArticles = getLikedArticles(user);
		return likedArticles.contains(article);
	}
	
}
