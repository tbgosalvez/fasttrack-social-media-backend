package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

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

	@Column(updatable = false)
	private Timestamp posted;

	private boolean deleted = false;

	@Column(nullable = true)
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
