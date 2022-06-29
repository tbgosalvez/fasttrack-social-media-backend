package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
	
	private final TweetService tweetService;
	private final UserService userService;
	
	@GetMapping
	public List<TweetResponseDto> getAllTweets(){
		return tweetService.getAllTweets();
	}
	
	@GetMapping("/{id}")
	public TweetResponseDto getTweetById(@PathVariable Long id) {
		return tweetService.getTweetResponseById(id);
	}
	
	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getReplies(@PathVariable Long id) {
		return tweetService.getReplies(id);
	}
	
	@GetMapping("/{id}/reposts")
	public List<TweetResponseDto> getReposts(@PathVariable Long id) {
		return tweetService.getReposts(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetReqDto) {
		userService.validateCredentials(tweetReqDto.getCredentials());
		return tweetService.createTweet(tweetReqDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto creds) {
		userService.validateCredentials(creds);
		return tweetService.deleteTweet(id, creds);
	}
}
