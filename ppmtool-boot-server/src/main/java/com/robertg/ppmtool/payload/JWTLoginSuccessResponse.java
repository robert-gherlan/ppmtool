package com.robertg.ppmtool.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class JWTLoginSuccessResponse {
	private boolean success;
	private String token;
}
