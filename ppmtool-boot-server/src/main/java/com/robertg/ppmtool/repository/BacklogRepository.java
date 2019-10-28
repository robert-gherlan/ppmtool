package com.robertg.ppmtool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robertg.ppmtool.domain.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

	Optional<Backlog> findByProjectIdentifier(String projectIdentifier);

}
