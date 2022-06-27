package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="user_table")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Embedded
	private Credentials credentials;
	
	@Embedded
	private Profile profile;

	@Column(updatable = false)
	private Timestamp joined;
	
	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;
	
	@ManyToMany
	@JoinTable(
			name = "user_likes",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name="tweet_id")
	)
	private List<Tweet> likedTweets;

	@ManyToMany
	@JoinTable(
			name = "user_mentions",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "tweet_id")
	)
	private List<Tweet> mentionedTweets;
	
	@ManyToMany
	@JoinTable(name = "followers_following")
	private List<User> followers;
	
	@ManyToMany(mappedBy = "followers")
	private List<User> following;
	
}
