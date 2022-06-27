package com.cooksys.socialmedia.entities;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {
	
	private String username;
	
	private String password;

}
