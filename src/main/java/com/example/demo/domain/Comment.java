package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {"article",}) 
@Table(name="comment")
public class Comment implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String content;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	private User user;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Article article;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}	
}
