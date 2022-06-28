package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	@OneToMany(mappedBy = "inReply")
	private List<Tweet> inReplyTo;
	
	@ManyToOne
	private Tweet inReply;
	
	@OneToMany(mappedBy = "repost")
	private List<Tweet> repostOf;
	
	@ManyToOne
	private Tweet repost;

	@ManyToMany(mappedBy = "tweetsInHashtag")
	private List<Hashtag> hashtags;

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedByUsers;

	@ManyToMany(mappedBy = "mentionedTweets")
	private List<User> mentionedByUsers;

}
