package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private User author;

	@CreatedDate
	private Timestamp posted = Timestamp.valueOf(LocalDateTime.now());

	private boolean deleted = false;

	private String content;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;
	
	@ManyToOne
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	@ManyToOne
	private Tweet repostOf;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="tweet_hashtags",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashtag_id")
	)
	private List<Hashtag> hashtags = new ArrayList<>();

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedByUsers = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "user_mentions",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> mentionedByUsers = new ArrayList<>();

}
