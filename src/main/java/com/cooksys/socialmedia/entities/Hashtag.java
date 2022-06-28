package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String label;

	@Column(updatable = false)
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;

	@ManyToMany
	@JoinTable(
			name="tweet_hashtags",
			joinColumns = @JoinColumn(name = "hashtag_id"),
			inverseJoinColumns = @JoinColumn(name = "tweet_id")
	)
	private List<Tweet> tweetsInHashtag;
}
