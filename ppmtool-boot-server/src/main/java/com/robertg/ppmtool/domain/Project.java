package com.robertg.ppmtool.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robertg.ppmtool.utils.DateUtils;

import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class Project implements Serializable {

	private static final long serialVersionUID = 5018481015991681491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Project name is required")
	private String name;

	@NotBlank(message = "Project identifier is required")
	@Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
	@Pattern(regexp = "\\w{4,5}", message = "Project identifier is invalid. Only characters are accepted.")
	@Column(updatable = false, unique = true)
	private String identifier;

	@NotBlank(message = "Project description is required")
	private String description;

	@Column(updatable = false)
	private String projectLeader;

	@NotNull(message = "The start date is required")
	@FutureOrPresent(message = "The due date must be a future or a present date")
	@JsonFormat(pattern = DateUtils.ISO_DATE_FORMAT)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate startDate;

	@NotNull(message = "The end date is required")
	@FutureOrPresent(message = "The due date must be a future or a present date")
	@JsonFormat(pattern = DateUtils.ISO_DATE_FORMAT)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate endDate;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
	private Backlog backlog;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.identifier = this.identifier.toUpperCase();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public String getUppercaseIdentifier() {
		return this.identifier.toUpperCase();
	}
}
