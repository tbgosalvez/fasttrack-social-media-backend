package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
	@Column(nullable = false,)
	private Timestamp firstUsed = Timestamp.valueOf(LocalDateTime.now());

	@LastModifiedDate
	@Column(nullable = false)
	private Timestamp lastUsed = Timestamp.valueOf(LocalDateTime.now());

	@ManyToMany
	@JoinTable(
			name="tweet_hashtags",
			joinColumns = @JoinColumn(name = "hashtag_id"),
			inverseJoinColumns = @JoinColumn(name = "tweet_id")
	)
	private List<Tweet> tweetsInHashtag;
}
