package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String label;

	@CreatedDate
	@Column(nullable = false)
	private Timestamp firstUsed = Timestamp.valueOf(LocalDateTime.now());

	@LastModifiedDate
	@Column(nullable = false)
	private Timestamp lastUsed = Timestamp.valueOf(LocalDateTime.now());

	@ManyToMany(mappedBy = "hashtags")
	private List<Tweet> tweets;
}
