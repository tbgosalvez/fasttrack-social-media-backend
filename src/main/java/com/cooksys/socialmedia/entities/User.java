package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Profile;

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
	
	@Column(unique = true)
	private String username;
	
	@Embedded
	private Credentials credentials;
	
	@Embedded
	private Profile profile;
	
	private Timestamp joined;
	
	@OneToMany(mappedBy = "user_table")
	private List<User> followers;
	
	@OneToMany(mappedBy = "user_table")
	private List<User> following;
	
	@OneToMany(mappedBy = "user_table")
	private List<Tweet> tweets;
	
	@OneToMany(mappedBy = "user_table")
	private List<User> likes;
	
	@OneToMany(mappedBy = "user_table")
	private List<User> mentions;
	
	
}
