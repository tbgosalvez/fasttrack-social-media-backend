package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

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

	@CreatedDate
	@Column(nullable = false)
	private Timestamp joined = Timestamp.valueOf(LocalDateTime.now());
	
	private boolean deleted = false;
	
	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;
	
	@ManyToMany
	@JoinTable(
			name = "user_likes",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name="tweet_id")
	)
	private List<Tweet> likedTweets = new ArrayList<>();

	@ManyToMany(mappedBy = "mentionedUsers")
	private List<Tweet> mentionedTweets = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "followers_following")
	private List<User> followers;
	
	@ManyToMany(mappedBy = "followers")
	private List<User> following;
	
}
