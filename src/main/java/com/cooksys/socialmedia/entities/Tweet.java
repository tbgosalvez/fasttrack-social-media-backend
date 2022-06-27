package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private User author;
	
	private Timestamp posted;
	
	@Column(nullable = true)
	private String content;
	
	// How to map these, OneToMany or ManyToOne
	@Column(nullable = true)
	private Tweet inReplyTo;
	
	@Column(nullable = true)
	private Tweet repostOf;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "tweet")
	private List<User> likes;
	
	@OneToMany(mappedBy = "tweet")
	private List<User> mentions;
	
	@OneToMany(mappedBy = "tweet")
	private List<Hashtag> hashtags;
	

}
