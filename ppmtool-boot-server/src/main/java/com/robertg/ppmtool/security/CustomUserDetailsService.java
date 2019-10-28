package com.robertg.ppmtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertg.ppmtool.domain.User;
import com.robertg.ppmtool.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userService.findByUsername(username);
	}

	@Transactional
	public User loadUserById(Long id) {
		return userService.findById(id);
	}
}
