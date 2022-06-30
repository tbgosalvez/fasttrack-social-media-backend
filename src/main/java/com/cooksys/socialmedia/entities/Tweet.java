package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	@ManyToMany
	@JoinTable(name = "tweet_hashtags", 
	joinColumns = @JoinColumn(name = "tweet_id"), 
	inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private List<Hashtag> hashtags;

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedByUsers;

	@ManyToMany
	@JoinTable(name = "user_mentions", 
	joinColumns = @JoinColumn(name = "tweet_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))

  private List<User> mentionedByUsers;
}
