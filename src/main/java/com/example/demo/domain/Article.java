package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name = "article")
public class Article implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "article")
	private List<Comment> comments = new ArrayList<>();
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column
	private String description = "没有描述";
	
	@Column(length = 1000)
	private String content = "无内容";
	
	@ManyToMany( mappedBy = "likedArticles")
	private Set<User> likedUsers = new HashSet<>();

	public Set<User> getLikedUsers() {
		return likedUsers;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
