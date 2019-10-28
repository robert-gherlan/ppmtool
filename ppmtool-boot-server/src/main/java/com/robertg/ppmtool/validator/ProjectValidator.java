package com.robertg.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.robertg.ppmtool.domain.Project;

@Component
public class ProjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Project project = (Project) object;
		if (!errors.hasErrors() && project.getStartDate().isAfter(project.getEndDate())) {
			errors.rejectValue("endDate", "Time", "The end date must be after start date");
		}
	}
}
