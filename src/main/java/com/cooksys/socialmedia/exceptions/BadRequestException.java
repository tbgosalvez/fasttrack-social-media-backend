package com.cooksys.socialmedia.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -4473099953347002333L;
	
	private String message;

}
