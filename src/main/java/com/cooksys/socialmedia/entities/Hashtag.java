package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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

	@ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL)
	private List<Tweet> tweets = new ArrayList<>();

	public Hashtag(String newLabel) {
		label = newLabel;
	}
}
