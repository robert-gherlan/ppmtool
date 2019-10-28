package com.robertg.ppmtool.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertg.ppmtool.domain.Backlog;
import com.robertg.ppmtool.domain.Project;
import com.robertg.ppmtool.domain.User;
import com.robertg.ppmtool.exceptions.ProjectIdException;
import com.robertg.ppmtool.exceptions.ProjectNotFoundException;
import com.robertg.ppmtool.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogService backlogService;

	@Autowired
	private UserService userService;

	public Project save(Project project, String username) {
		if (project.getId() != null) {
			Optional<Project> existingProject = projectRepository.findById(project.getId());
			if (existingProject.isPresent() && !existingProject.get().getProjectLeader().equals(username)) {
				throw new ProjectNotFoundException("Project not found in your accound.");
			} else if (!existingProject.isPresent()) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getIdentifier()
						+ "' cannot be updated because is assigned to other user.");
			}
		}

		try {
			User user = userService.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(username);
			if (Objects.isNull(project.getId())) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getUppercaseIdentifier());
			} else {
				Backlog foundBacklog = backlogService.findByProjectIdentifier(project.getUppercaseIdentifier());
				project.setBacklog(foundBacklog);
			}

			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getIdentifier() + "' already exists");
		}
	}

	public Project findByIdentifier(String identifier, String username) {
		return projectRepository.findByIdentifierAndProjectLeader(identifier, username)
				.orElseThrow(() -> new ProjectIdException("Project ID '" + identifier + "' does not exist"));
	}

	public List<Project> findAll(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteByIdentifier(String identifier, String username) {
		Project project = findByIdentifier(identifier, username);
		projectRepository.delete(project);
	}
}
