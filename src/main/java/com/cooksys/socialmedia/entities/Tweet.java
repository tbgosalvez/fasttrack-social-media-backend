package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private User author;
	
	private Timestamp posted;
	
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
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinColumn(name = "hashtag_id")
	private List<Hashtag> hashtags;
	
	private boolean deleted = false;


}
