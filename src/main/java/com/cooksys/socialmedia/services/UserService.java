package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
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

  List<UserResponseDto> getUserFollowers(String username);

  List<User> updateUsers(List<User> users);

	UserResponseDto updateUser(String username, UserRequestDto incomingUser);
}
