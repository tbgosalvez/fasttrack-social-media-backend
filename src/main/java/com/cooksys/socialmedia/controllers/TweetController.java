package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
	
	private final TweetService tweetService;
	private final UserService userService;
	
	@GetMapping
	public List<TweetResponseDto> getAllTweets(){
		return tweetService.getAllTweetResponseDtos();
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
	
	@GetMapping("/{id}/mentions")
	public List<UserResponseDto> getMentionedUsers(@PathVariable Long id) {
		return tweetService.getMentionedUsers(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getLikedByUsers(@PathVariable Long id) {
		return tweetService.getLikedByUsers(id);
	}
	
	@GetMapping("/{id}/tags")
	public List<HashtagDto> getTags(@PathVariable Long id) {
		return tweetService.getTags(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto newTweet) {
		userService.validateCredentials(newTweet.getCredentials());
		return tweetService.createTweet(newTweet);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto creds) {
		userService.validateCredentials(creds);
		return tweetService.deleteTweet(id, creds);
	}
}
