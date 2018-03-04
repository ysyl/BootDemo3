package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleRepository;
import com.example.demo.domain.Authority;
import com.example.demo.domain.AuthorityRepository;
import com.example.demo.domain.Comment;
import com.example.demo.domain.CommentRepository;
import com.example.demo.domain.Notice;
import com.example.demo.domain.NoticeType;
import com.example.demo.domain.User;
import com.example.demo.domain.UserNotify;
import com.example.demo.domain.UserRepository;
import com.example.demo.service.NotifyService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthorityRepository authorityReposity;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	NotifyService notifyService;
	
	
	/*@GetMapping("/userList")
	public String showUserList(Principal principal, Model model)
	{

		List<User> userList = userService.findAll();
		User userDetails = (User)((Authentication) principal).getPrincipal();
		User user = userRepository.findById(userDetails.getId());
		model.addAttribute(userList);
		model.addAttribute("user", user); 
		return "userList";
	}*/
	
	@GetMapping("/create")
	public String showCreateForm(ModelMap modelMap)
	{
		modelMap.addAttribute("user", new User());
		modelMap.addAttribute("action", "create");
		return "userForm";
	}	
	
	@PostMapping("/create")
	public String createUser(@ModelAttribute User user)
	{
		Authority auth = authorityReposity.findByName("ROLE_USER");
		List<Authority> authList = new ArrayList<>();
		authList.add(auth);
		user.setAuthorities(authList);
		userRepository.save(user);	
		return "redirect:/users/userList";
	}
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable int id, ModelMap modelMap)
	{
		User user = userService.findUser(id);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("action", "update");
		return "userForm";
	}	
	
	@PostMapping("/update")
	public String updateUser(@ModelAttribute User user)
	{
		userService.save(user);
		return "redirect:/admins/allUser";
	}
	
	@RequestMapping("/")
	public String index()
	{
		return "index";
	}
	
	@GetMapping("/profile/user_id-{user_id}")
	public String showUserProfile(@PathVariable("user_id") int userId,
			Principal principal, 
			Model model)
	{
		User user = userRepository.findById(userId);
		List<Article> articles = articleRepository.findByUser_id(userId);
		List<Comment> comments = commentRepository.findByUser_id(userId);
		
		User me = (User)((Authentication) principal).getPrincipal();
		
		List<Notice> announces = notifyService.pullAnnounce(user);
		
		//抓取提醒
		List<Notice> reminds = new ArrayList<>();
		try {
			reminds = notifyService.pullRemind(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean isMe = userId == me.getId();
		
		model.addAttribute("user", user);
		model.addAttribute("articles", articles);
		model.addAttribute("comments", comments);
		model.addAttribute("announces", announces);
		model.addAttribute("isMe", isMe);
		model.addAttribute("reminds", reminds);
		return "userProfile";
	}
	
}
