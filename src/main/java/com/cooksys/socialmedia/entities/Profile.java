package com.cooksys.socialmedia.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
	
	@Column(nullable = true)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	private String email;
	
	@Column(nullable = true)
	private String phone;

}
