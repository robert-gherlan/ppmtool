package com.robertg.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -8946648758217880348L;

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

}
