package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;

public interface UserService {

	List<UserResponseDto> getAllActiveUserDtos();

	List<User> getAllActiveUsers();

	void validateCredentials(CredentialsDto creds) throws NotAuthorizedException;

	List<TweetResponseDto> getUserMentions(String username);

	UserResponseDto getUserByName(String username);

	User getUserByCredentials(CredentialsDto creds) throws NotFoundException;

	UserResponseDto createUser(UserRequestDto userRequestDto);

	List<UserResponseDto> getUserFollowing(String username);

  List<User> updateUsers(List<User> users);

	UserResponseDto updateUser(String username, UserRequestDto incomingUser);

  List<UserResponseDto> getUserFollowers(String username);

  List<TweetResponseDto> getUserFeed(String username);

  String setFollowing(String username, CredentialsDto followingUser);

	String setUnfollow(String username, CredentialsDto unfollowUser);

  UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) throws NotFoundException;

	void likeTweet(Tweet tweet, User user);
}
