package com.example.demo.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {
		"password", "authorities", "articles", 
		"comments", "accountNonLocked", "credentialsNonExpired",
		"accountNonExpired", "enabled", "username"})
@Table(name = "user")
public class User implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public User()
	{
		
	}
	
	public User(String username, String password, String name, int age, List<Authority> authorities)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
		this.authorities = authorities;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String username;	
	
	@Column(nullable = false)
	private String name = "default";
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false)
	private int age = 10;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
	private List<Authority> authorities;
	
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private List<Article> articles;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private List<Comment> comments;
	
	@ManyToMany
	@JoinTable(name = "user_liked_articless", 
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "article_id", referencedColumnName = "id")})
	private Set<Article> likedArticles = new HashSet<>();
	
	
	public Set<Article> getLikedArticles() {
		return likedArticles;
	}
	
	public void setLikedArticle(Set<Article> articles) {
		likedArticles = articles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}	
}
