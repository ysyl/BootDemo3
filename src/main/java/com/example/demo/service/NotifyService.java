package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleRepository;
import com.example.demo.domain.Notice;
import com.example.demo.domain.NoticeActionType;
import com.example.demo.domain.NoticeRepository;
import com.example.demo.domain.NoticeTargetType;
import com.example.demo.domain.NoticeType;
import com.example.demo.domain.Subscription;
import com.example.demo.domain.SubscriptionRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserNotify;
import com.example.demo.domain.UserNotifyRepository;
import com.example.demo.domain.UserRepository;

@Service
public class NotifyService {
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	UserNotifyRepository userNotifyRepository;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	//插入初始数据
	@PostConstruct
	private void init()  {
		User sender = userRepository.findByUsername("admin");
		createAnnounce("testAnnounces", sender);	
		createAnnounce("test wao", sender);	
		createAnnounce("disangegonggao", sender);	
		createAnnounce("coool", sender);	
		
		//获取用户的文章，取其中之一插入like通知,通知的发送者为admin1；
		User admin1 = userRepository.findByUsername("admin1");
		List<Article> adminArticles = articleRepository.findByUser_id(sender.getId());
		Long articleId = adminArticles.get(0).getId();
		createSubscription(articleId, NoticeTargetType.article, NoticeActionType.like, sender);
		createRemind(articleId, NoticeTargetType.article, NoticeActionType.like, admin1);
		try {
			pullRemind(sender);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			pullRemind(admin1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void createAnnounce(String content, User sender) {
		Date createAt = new Date();
		Notice announce = new Notice(NoticeType.annouce, content, sender, createAt);
		noticeRepository.save(announce);
		
		List<User> users = userRepository.findAll();
		List<UserNotify> userNotifies = new ArrayList<>();
		
		
		for (User user : users) {
			UserNotify userNotify = new UserNotify(user, announce, createAt);
			userNotifies.add(userNotify);
		}
		userNotifyRepository.save(userNotifies);		
	}
		
	public List<Notice> pullAnnounce(User user) {
		List<UserNotify> userNotifies = userNotifyRepository.findAllByNotify_typeOrderByCreateAt(NoticeType.annouce);
		Date lastTime;
		if ( userNotifies.isEmpty() ) {
			lastTime = new Date(0);
		} else {
			lastTime = new Date(0); 
		}
		List<Notice> announces = noticeRepository
				.findAllByTypeAndCreateAtAfter(NoticeType.annouce, lastTime);
		return announces;
	}
	
	public List<Notice> pullAllAnnounce() {
		List<Notice> announces = noticeRepository.findAll();
		
		return announces;
	}
	
	//创建提醒
	//参数：目标Id，目标类型，动作，发送者
	public void createRemind(Long target, NoticeTargetType targetType, NoticeActionType action, User sender) {
		Date createAt = new Date();
		
		Notice remind = new Notice(NoticeType.remind, target, targetType.article, action, sender, createAt);
		noticeRepository.save(remind);
	}
		
		public void createLike(Long target, User sender) {
			Article articleLiked = articleRepository.findById(target);
			articleRepository.findAllLikedArticleByUserId(sender.getId()).add(articleLiked);
			userRepository.save(sender);
			createRemind(target, NoticeTargetType.article, NoticeActionType.like, sender);
		}
	
	//根据订阅，抓取消息，同时添加进UserNotify
	public List<Notice> pullRemind(User user) throws Exception {
		Date lastTime = new Date(0); //TODO
		
		List<Subscription> subscriptionList = subscriptionRepository.findAllByUser_id(user.getId());
		
		if (subscriptionList.isEmpty())
			throw new Exception("找不到订阅");
		
		Long target;
		NoticeTargetType targetType;
		NoticeActionType action;
		List<Notice> resultRemind = new ArrayList<>();
		
		for (Subscription subscription : subscriptionList) {
			target = subscription.getTarget();
			targetType = subscription.getTargetType();
			action = subscription.getAction();
			
			List<Notice> reminds = noticeRepository.
					findAllByTargetAndTargetTypeAndActionAndCreateAtAfter(target, targetType, action, lastTime);
			resultRemind.addAll(reminds);
		}
		
		List<UserNotify> userNotifyList = new ArrayList<>();
		for (Notice remind : resultRemind) {
			UserNotify userNotify = new UserNotify(user, remind, lastTime);
			userNotifyList.add(userNotify);
		}
		userNotifyRepository.save(userNotifyList);
		
		return resultRemind;
	}
	
	//创建订阅 
	public void createSubscription(Long target, NoticeTargetType targetType, NoticeActionType action, User user) {
		Date createAt = new Date(0);
		Subscription subscription = new Subscription(target, targetType, action, user, createAt);
		
		subscriptionRepository.save(subscription);
	}
	
	
	//创建信息，将信息加入UserNotify
	public void createMessage(String content, User sender, User receiver) {
		Date createAt = new Date();
		//TODO		
	}
	
	
}
