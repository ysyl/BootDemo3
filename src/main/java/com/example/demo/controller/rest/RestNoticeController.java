package com.example.demo.controller.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.demo.domain.Notice;
import com.example.demo.domain.NoticeActionType;
import com.example.demo.domain.NoticeTargetType;
import com.example.demo.domain.User;
import com.example.demo.service.NotifyService;

@RestController
@RequestMapping("/notice")
public class RestNoticeController {
	
	@Autowired
	NotifyService notifyService;
	
	@GetMapping("/post-remind")
	public boolean postRemind(@RequestParam(value = "action", required = true) String actionStr,
							@RequestParam(value = "article_id", required = true) Long articleId,
							Principal principal) {
		NoticeActionType action = NoticeActionType.valueOf(actionStr);
		User sender = (User)((Authentication) principal).getPrincipal();
		
		notifyService.createLike(articleId,  sender);
		return true;
	}
	
	@GetMapping("/get-remind")
	public List<Notice> getRemind(Principal principal) {
		User user = (User)((Authentication) principal).getPrincipal();
		
		List<Notice> reminds = new ArrayList<>();
		try {
			reminds = notifyService.pullRemind(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reminds;
	}
	
	@GetMapping("/post-remind2")
	public String test() {
		return "test";
	}
}
