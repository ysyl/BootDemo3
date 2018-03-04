package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleRepository;
import com.example.demo.domain.Comment;
import com.example.demo.domain.CommentRepository;
import com.example.demo.domain.Notice;
import com.example.demo.domain.NoticeActionType;
import com.example.demo.domain.NoticeRepository;
import com.example.demo.domain.NoticeTargetType;
import com.example.demo.domain.NoticeType;
import com.example.demo.domain.User;
import com.example.demo.domain.UserNotify;
import com.example.demo.domain.UserNotifyRepository;
import com.example.demo.domain.UserRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.NotifyService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	UserNotifyRepository userNotifyRepository;
	
	@Autowired
	NotifyService notifyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ArticleService articleService;
	
	@GetMapping("/all")
	public String getAllArticles(Principal principal,Model model, RedirectAttributes redirectAttr)
	{
		List<Article> articles = articleRepository.findAll();
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		
		List<Notice> announces = notifyService.pullAnnounce(user);
		int announcesCount = announces.size();
		
		//抓取通知
		List<Notice> reminds = new ArrayList<>();
		try {
			reminds = notifyService.pullRemind(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("articles", articles);
		model.addAttribute("user",user);
		model.addAttribute("notifyCount", announcesCount);		
		model.addAttribute("announces", announces);
		model.addAttribute("reminds", reminds);
		return "allArticles";
	}
	
	@GetMapping("/post")
	public String getPostPage(Principal principal, Model model)
	{
		User user = userRepository.findByUsername(principal.getName());
		model.addAttribute("articles", new Article());
		model.addAttribute("user", user);
		return "postArticle";
	}
	
	@PostMapping("/post/{user_id}")
	public String postArticle(@PathVariable("user_id") int userId,@ModelAttribute Article article, Model model)
	{
		User user = userRepository.findById(userId);
		article.setCreationDate(new Date());
		article.setLastUpdate(new Date());
		article.setUser(user);
		Article savedArticle = articleRepository.save(article);
		
		Long target = savedArticle.getId();
		NoticeTargetType targetType = NoticeTargetType.article;
		
		for (NoticeActionType action : NoticeActionType.values()) {
			notifyService.createSubscription(target, targetType, action, user);
		}
		
		return "redirect:/articles/all";
	}
	
	@GetMapping("/article_id-{article_id}")
	public String showArticleById(@PathVariable("article_id") Long id, Model model)
	{
		Article article = articleRepository.findById(id);
		User user = userRepository.findByArticles_id(id);
		Comment comment = new Comment();
		comment.setUser(user);
		boolean isLiked = userService.isLikedThisArticle(user, article);
		Long likedCount = articleService.countLikedUser(article);
		
		model.addAttribute("article", article);
		model.addAttribute("user", user);
		model.addAttribute("comment", comment);
		model.addAttribute("isLiked", isLiked);
		model.addAttribute("likedCount", likedCount);
		return "articlePage";
	}
	
	@PostMapping("/post_comment")
	public String postComment(@ModelAttribute(name = "comment") Comment comment, 
			@ModelAttribute(name = "article_id") Long articleId, 
			Principal principal, 			
			Model model)
	{
		User user = (User)((Authentication) principal).getPrincipal();
		Article article = articleRepository.findById(articleId);
		comment.setUser(user);
		comment.setArticle(article);
		commentRepository.save(comment);
		model.addAttribute("comment", comment);
		model.addAttribute("user", user);
		return "redirect:/articles/article_id-" + String.valueOf(articleId);
	}
}
