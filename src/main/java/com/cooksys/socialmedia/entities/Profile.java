package com.cooksys.socialmedia.entities;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private boolean deleted = false;

}
