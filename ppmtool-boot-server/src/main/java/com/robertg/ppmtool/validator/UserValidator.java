package com.robertg.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.robertg.ppmtool.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;
		if (!errors.hasErrors()) {
			if (!StringUtils.hasText(user.getPassword())) {
				errors.rejectValue("password", "Length", "Password is required.");
				return;
			}

			if (user.getPassword().length() < 6) {
				errors.rejectValue("password", "Length", "Password must be at least 6 characters.");
				return;
			}

			if (!user.getPassword().equals(user.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "Match", "Passwords must match.");
			}
		}
	}
}
