package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	private Timestamp joined;
	
	@OneToMany(mappedBy = "user")
	private List<Tweet> tweets;
	
	@ManyToMany
	@JoinColumn(name = "tweet_id")
	private List<Tweet> likes;
	
	@ManyToMany
	@JoinTable
	private List<User> mentions;
	
	@ManyToMany
	@JoinTable
	private List<User> followers;
	
	@ManyToMany(mappedBy = "followers")
	private List<User> following;
	
}
