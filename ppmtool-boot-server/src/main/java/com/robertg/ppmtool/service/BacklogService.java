package com.robertg.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertg.ppmtool.domain.Backlog;
import com.robertg.ppmtool.exceptions.ProjectNotFoundException;
import com.robertg.ppmtool.repository.BacklogRepository;

@Service
@Transactional
public class BacklogService {

	@Autowired
	private BacklogRepository backlogRepository;

	public Backlog findByProjectIdentifier(String projectIdentifier) {
		return backlogRepository.findByProjectIdentifier(projectIdentifier).orElseThrow(
				() -> new ProjectNotFoundException("Backlog for project ID '" + projectIdentifier + "' was not found"));
	}

	public List<Backlog> findAll() {
		return backlogRepository.findAll();
	}

	public Backlog save(Backlog backlog) {
		return backlogRepository.save(backlog);
	}
}
