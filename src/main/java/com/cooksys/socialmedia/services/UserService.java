package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;

import java.util.List;

public interface UserService {

	List<UserResponseDto> getAllActiveUsers();

    void validateCredentials(CredentialsDto creds) throws NotAuthorizedException;

    UserResponseDto getUsername(String username);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getUserFollowing(String username);

    List<UserResponseDto> getUserFollowers(String username);

    List<TweetResponseDto> getUserTweets(String username);
}
