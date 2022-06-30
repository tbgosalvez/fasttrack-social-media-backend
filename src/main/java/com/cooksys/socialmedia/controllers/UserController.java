package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	private final TweetService tweetService;

	@GetMapping
	public List<UserResponseDto> getAllActiveUsers() {
		return userService.getAllActiveUserDtos();
	}

	
	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getUserMentions(@PathVariable String username) {
		return userService.getUserMentions(username);
	}
	
	@GetMapping("/@{username}")
	public UserResponseDto getUserByName(@PathVariable String username) {
		return userService.getUserByName(username);
	}

	@PostMapping
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}

	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getUserFollowing(@PathVariable String username) {
		return userService.getUserFollowing(username);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username) {
		return userService.getUserFollowers(username);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getUserTweets(@PathVariable String username) {
		return tweetService.getUserTweets(username);
	}

	
  @GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getUserFeed(@PathVariable String username) {
		return userService.getUserFeed(username);
	}

	@PostMapping("/@{username}/follow")
	public String setFollowing(@PathVariable String username, @RequestBody CredentialsDto followingUser) {
		userService.validateCredentials(followingUser);
		return userService.setFollowing(username, followingUser);
	}

	@PostMapping("/@{username}/unfollow")
	public String setUnfollow(@PathVariable String username, @RequestBody CredentialsDto unfollowUser) {
		userService.validateCredentials(unfollowUser);
		return userService.setUnfollow(username, unfollowUser);

	@DeleteMapping("/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.validateCredentials(credentialsDto);
		return userService.deleteUser(username, credentialsDto);
	}
}
