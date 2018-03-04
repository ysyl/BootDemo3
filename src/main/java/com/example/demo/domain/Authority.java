package com.example.demo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@Entity
@JsonIgnoreType
@Table(name = "authority")
public class Authority implements GrantedAuthority, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany
	private List<User> userList = new ArrayList<>();
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name;
	}
	
	protected Authority()
	{
		
	}
	
	public Authority(String name)
	{
		this.name = name;
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
		
	public void addUser(User user)
	{
		userList.add(user);
	}

}
