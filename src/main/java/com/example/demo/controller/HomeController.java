package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Authority;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;


@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	public String hello(Model model)
	{
		
		return "Login";
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	
	
	@RequestMapping("/")
	public String index(Model model)
	{
		List<User> userList = userRepository.findAll();
		model.addAttribute(userList);
		return "redirect:/articles/all";
	}
	
	@GetMapping("/registry")
	public String showRegistry(Model model)
	{
		model.addAttribute("user", new User());
		return "registry";
	}
	
	@PostMapping("/registry")
	public String registry(@ModelAttribute User user)
	{
		List<Authority> authorities = new ArrayList<>();
		authorities.add(new Authority("ROLE_USER")); 
		User userDetails = new User(user.getUsername(), 
				user.getPassword(), 
				user.getName(), 
				user.getAge(),
				authorities); 
		userRepository.save(userDetails);
		return "redirect:/login"; 
	}
}
