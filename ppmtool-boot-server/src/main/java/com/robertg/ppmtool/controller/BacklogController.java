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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertg.ppmtool.domain.Backlog;
import com.robertg.ppmtool.domain.ProjectTask;
import com.robertg.ppmtool.service.BacklogService;
import com.robertg.ppmtool.service.ProjectTaskService;
import com.robertg.ppmtool.utils.ErrorFieldMapperUtils;

@CrossOrigin
@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

	@Autowired
	private BacklogService backlogService;

	@Autowired
	private ProjectTaskService projectTaskService;

	@GetMapping
	public ResponseEntity<List<Backlog>> findAll() {
		return new ResponseEntity<>(backlogService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/{backlogId}")
	public ResponseEntity<Object> createNewBacklog(@Valid @RequestBody ProjectTask projectTask,
			BindingResult bindingResult, @PathVariable String backlogId, Principal principal) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorFieldMapperUtils.mapFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
		}

		ProjectTask savedProjectTask = projectTaskService.save(backlogId, projectTask, principal.getName());
		return new ResponseEntity<>(savedProjectTask, HttpStatus.CREATED);
	}

	@GetMapping("/{backlogId}")
	public ResponseEntity<List<ProjectTask>> getProjectTasks(@PathVariable String backlogId, Principal principal) {
		return new ResponseEntity<>(projectTaskService.findBacklogById(backlogId, principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{backlogId}/project-task/{projectTaskId}")
	public ResponseEntity<ProjectTask> getProjectTask(@PathVariable String backlogId,
			@PathVariable String projectTaskId, Principal principal) {
		ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlogId, projectTaskId,
				principal.getName());
		return new ResponseEntity<>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{backlogId}/project-task/{projectTaskId}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
			@PathVariable String backlogId, @PathVariable String projectTaskId, Principal principal) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorFieldMapperUtils.mapFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
		}

		ProjectTask updatedTask = projectTaskService.updateProjectTaskByProjectSequence(projectTask, backlogId,
				projectTaskId, principal.getName());
		return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	}

	@DeleteMapping("/{backlogId}/project-task/{projectTaskId}")
	public ResponseEntity<String> deleteProjectTask(@PathVariable String backlogId, @PathVariable String projectTaskId,
			Principal principal) {
		projectTaskService.deleteProjectTaskByProjectSequence(backlogId, projectTaskId, principal.getName());
		return new ResponseEntity<>("Project Task " + projectTaskId + " was deleted successfully", HttpStatus.OK);
	}
}
