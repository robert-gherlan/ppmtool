package com.robertg.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertg.ppmtool.domain.User;
import com.robertg.ppmtool.payload.JWTLoginSuccessResponse;
import com.robertg.ppmtool.payload.LoginRequest;
import com.robertg.ppmtool.security.JwtTokenProvider;
import com.robertg.ppmtool.security.SecurityConstants;
import com.robertg.ppmtool.service.UserService;
import com.robertg.ppmtool.utils.ErrorFieldMapperUtils;
import com.robertg.ppmtool.validator.UserValidator;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorFieldMapperUtils.mapFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		String authenticationHeader = SecurityConstants.BEARER_TOKEN_PREFIX + token;
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, authenticationHeader));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorFieldMapperUtils.mapFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}
}