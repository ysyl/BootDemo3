package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService
{

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String name)
			throws org.springframework.security.core.userdetails.UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.example.demo.domain.User user = userRepository.findByUsername(name);
		if (user != null)
		{
			return user;
		}
		
		throw new UsernameNotFoundException("not found ");
	}
	
	public void save(User user)	
	{
		userRepository.save(user);
	}
}
