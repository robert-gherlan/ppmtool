package com.robertg.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertg.ppmtool.domain.Backlog;
import com.robertg.ppmtool.domain.ProjectTask;
import com.robertg.ppmtool.exceptions.ProjectNotFoundException;
import com.robertg.ppmtool.repository.ProjectTaskRepository;

@Service
@Transactional
public class ProjectTaskService {

	@Autowired
	private BacklogService backlogService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask save(String projectIdentifier, ProjectTask projectTask, String username) {
		Backlog foundBacklog = projectService.findByIdentifier(projectIdentifier, username).getBacklog();
		projectTask.setBacklog(foundBacklog);
		int backlogSequence = foundBacklog.getPtSequence();
		backlogSequence++;
		foundBacklog.setPtSequence(backlogSequence);
		backlogService.save(foundBacklog);
		projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		return projectTaskRepository.save(projectTask);
	}

	public List<ProjectTask> findBacklogById(String backlogId, String username) {
		projectService.findByIdentifier(backlogId, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
	}

	private ProjectTask findByProjectSequence(String projectSequence) {
		return projectTaskRepository.findByProjectSequence(projectSequence)
				.orElseThrow(() -> new ProjectNotFoundException("Project Task '" + projectSequence + "' not found"));
	}

	public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectTaskId, String username) {
		projectService.findByIdentifier(backlogId, username);
		ProjectTask projectTask = findByProjectSequence(projectTaskId);
		if (!projectTask.getProjectIdentifier().equals(backlogId)) {
			throw new ProjectNotFoundException(
					"Project Task '" + projectTaskId + "' does not exist in project '" + backlogId + "'");
		}
		return projectTask;
	}

	public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask, String backlogId,
			String projectTaskId, String username) {
		findProjectTaskByProjectSequence(backlogId, projectTaskId, username);
		return projectTaskRepository.save(updatedTask);
	}

	public void deleteProjectTaskByProjectSequence(String backlogId, String projectTaskId, String username) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectTaskId, username);
		projectTaskRepository.delete(projectTask);
	}
}
