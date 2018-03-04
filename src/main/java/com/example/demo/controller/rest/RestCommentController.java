package com.example.demo.controller.rest;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleRepository;
import com.example.demo.domain.Comment;
import com.example.demo.domain.CommentRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

import org.springframework.security.core.Authentication;

@RestController
public class RestCommentController {
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@GetMapping("/comments/article_id-{article-id}")
	public Map<String, Object> getComments(@PathVariable("article-id") Long articleId, Principal principal)
	{
		List<Comment> comments = commentRepository.findByArticle_id(articleId);
		User user = (User)((Authentication) principal).getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("comments", comments);
		map.put("user-id", user.getId());
		return map;
	}
	
	@PostMapping("/comments/article_id-{article-id}")
	public Map<String, Object> postComments(@PathVariable("article-id") Long articleId, @RequestBody Comment comment,Principal principal)
	{
		User user = (User) ((Authentication) principal).getPrincipal();
		Article article = articleRepository.findById(articleId);
		comment.setUser(user);
		comment.setArticle(article);
		commentRepository.save(comment);
		
		
		Map<String, Object> map = new HashMap<>();
		List<Comment> comments = commentRepository.findByArticle_id(articleId);
		
		map.put("comments", comments);
		map.put("user-id", user.getId());
		return map;
	}
}
