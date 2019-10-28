package com.robertg.ppmtool.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robertg.ppmtool.utils.DateUtils;

import lombok.Data;

@Data
@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Email(message = "Username needs to be an email")
	@NotBlank(message = "Username is required")
	@Column(unique = true, nullable = false)
	private String username;

	@NotBlank(message = "Full name is required")
	private String fullname;

	@NotBlank(message = "Password is required")
	private String password;

	@JsonIgnore
	private boolean enabled = true;

	@JsonIgnore
	private boolean locked;

	@Transient
	private String confirmPassword;

	@JsonFormat(pattern = DateUtils.ISO_DATE_FORMAT)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate expiryAt;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
	private List<Project> projects;

	@PrePersist
	protected void prePersist() {
		this.createdAt = LocalDateTime.now();
		// Set the expired date after three months.
		this.expiryAt = LocalDate.now().plusMonths(3);
	}

	@PreUpdate
	protected void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return expiryAt.isAfter(LocalDate.now());
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return enabled;
	}
}
