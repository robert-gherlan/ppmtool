package com.robertg.ppmtool.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertg.ppmtool.domain.Project;
import com.robertg.ppmtool.service.ProjectService;
import com.robertg.ppmtool.utils.ErrorFieldMapperUtils;
import com.robertg.ppmtool.validator.ProjectValidator;

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectValidator projectValidator;

	@GetMapping
	public ResponseEntity<List<Project>> findAll(Principal principal) {
		return new ResponseEntity<>(projectService.findAll(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{identifier}")
	public ResponseEntity<Project> findProjectByIdentifier(@PathVariable String identifier, Principal principal) {
		return new ResponseEntity<>(projectService.findByIdentifier(identifier, principal.getName()), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult,
			Principal principal) {
		projectValidator.validate(project, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorFieldMapperUtils.mapFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
		}

		Project savedProject = projectService.save(project, principal.getName());
		return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
	}

	@DeleteMapping("/{identifier}")
	public ResponseEntity<String> deleteProjectByIdentifier(@PathVariable String identifier, Principal principal) {
		projectService.deleteByIdentifier(identifier, principal.getName());
		return new ResponseEntity<>("Project ID '" + identifier + "' was successfully deleted.", HttpStatus.OK);
	}
}
