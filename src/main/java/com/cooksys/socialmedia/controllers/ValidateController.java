package com.cooksys.socialmedia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.services.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {
	
	private final ValidateService validateService;
	
	@GetMapping("/tag/exists/{label}")
	public boolean doesHashtagExist(@PathVariable String label) {
		return validateService.doesHashtagExist(label);
	}
	
	@GetMapping("/username/exists/@{username}")
	public boolean doesUsernameExist(@PathVariable String username) {
		return validateService.doesUsernameExist(username);
	}
	
	@GetMapping("/username/available/@{username}")
	public boolean isUsernameAvaialable(@PathVariable String username) {
		return validateService.isUserNameAvailable(username);
		
	}

}
