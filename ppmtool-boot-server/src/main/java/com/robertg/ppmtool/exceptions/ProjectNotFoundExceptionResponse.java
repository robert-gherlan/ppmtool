package com.robertg.ppmtool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectNotFoundExceptionResponse {

	private String projectNotFound;
}
