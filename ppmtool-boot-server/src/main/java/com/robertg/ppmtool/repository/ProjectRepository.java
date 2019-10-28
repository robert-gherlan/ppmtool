package com.robertg.ppmtool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robertg.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findAllByProjectLeader(String username);

	Optional<Project> findByIdentifierAndProjectLeader(String identifier, String username);
}
