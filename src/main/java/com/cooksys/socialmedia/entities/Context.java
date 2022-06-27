package com.cooksys.socialmedia.entities;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Context {

	private Tweet target;
	
	private Tweet before;
	
	private Tweet after;
	
}
