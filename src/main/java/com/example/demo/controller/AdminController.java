package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.service.NotifyService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	NotifyService notifyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/allUser")
	public String showUserList(Principal principal, Model model)
	{

		List<User> userList = userService.findAll();
		User userDetails = (User)((Authentication) principal).getPrincipal();
		User user = userRepository.findById(userDetails.getId());
		model.addAttribute(userList);
		model.addAttribute("user", user); 
		return "userList";
	}
	
	@GetMapping("/announces")
	public String announcePage(Principal principal ,Model model) {
		User me = (User)((Authentication) principal).getPrincipal();
		
		return "announceCenter";
	}
}
