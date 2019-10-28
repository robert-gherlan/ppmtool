package com.robertg.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertg.ppmtool.domain.User;
import com.robertg.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.robertg.ppmtool.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User save(User user) {
		try {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			return userRepository.save(user);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + user.getUsername() + "' already exists");
		}
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("The user was not found"));
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("The user was not found"));
	}
}
