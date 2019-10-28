package com.robertg.ppmtool.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robertg.ppmtool.utils.DateUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ProjectTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(updatable = false, unique = true)
	private String projectSequence;

	@NotBlank(message = "The project summary is required")
	private String summary;

	@NotBlank(message = "The acceptance criteria is required")
	private String acceptanceCriteria;

	@NotBlank(message = "Status is required")
	private String status;

	@Min(value = 1, message = "Priority is required")
	@Max(value = 3, message = "Priority is required")
	private int priority;

	@NotNull(message = "The due date is required")
	@FutureOrPresent(message = "The due date must be a future or a present date")
	@JsonFormat(pattern = DateUtils.ISO_DATE_FORMAT)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dueDate;

	@Column(updatable = false)
	private String projectIdentifier;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updateAt;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	private Backlog backlog;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", createdAt=" + createdAt
				+ ", updateAt=" + updateAt + "]";
	}

}
