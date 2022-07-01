package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllActiveUserDtos();

    List<User> getAllActiveUsers();

    List<TweetResponseDto> getUserMentions(String username);

    UserResponseDto getUserByName(String username);

    User getUserByCredentials(CredentialsDto creds) throws NotAuthorizedException;

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getUserFollowing(String username);

    List<User> updateUsers(List<User> users);

    UserResponseDto updateUser(String username, UserRequestDto incomingUser);

    List<UserResponseDto> getUserFollowers(String username);

    List<TweetResponseDto> getUserFeed(String username);

    void setFollowing(String username, CredentialsDto followingUser);

    void setUnfollow(String username, CredentialsDto unfollowUser);

    UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) throws NotFoundException;

    void likeTweet(Tweet tweet, User user);
}
