package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorDto {
	
	private String message;
	
	public ErrorDto(String string) {
		message = string;
	}

}
