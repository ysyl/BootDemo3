package com.example.demo.controller.rest;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.demo.domain.Notice;
import com.example.demo.domain.User;
import com.example.demo.service.NotifyService;

@RestController
@RequestMapping("/announces")
public class RestAdminController {
	
	@Autowired
	NotifyService notifyService;

	@GetMapping("/all")
	public List<Notice> allAnnounces(Principal principal) {
		User me = (User)((Authentication) principal).getPrincipal();
		List<Notice> announces = notifyService.pullAllAnnounce();
		
		return announces;
	}
	
	@PostMapping("/post_announce")
	public List<Notice> postAnnounce(@RequestBody Map<String, String> announce, Principal principal) {
		String content = announce.get("content");
		User sender = (User)((Authentication) principal).getPrincipal();
		notifyService.createAnnounce(content, sender);
		
		return notifyService.pullAllAnnounce();
	}
}
