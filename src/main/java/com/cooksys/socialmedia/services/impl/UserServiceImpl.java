package com.cooksys.socialmedia.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	
	@Override
	public List<UserResponseDto> getAllActiveUsers() {
		List<User> activeUsers =
				userRepository.findAll()
						.stream()
						.filter(user -> !user.isDeleted())
						.toList();

		return userMapper.entitiesToDtos(activeUsers);
	}

	// on success, do nothing for now
	@Override
	public void validateCredentials(CredentialsDto creds) throws NotAuthorizedException {
		if(userRepository.findByCredentials(credentialsMapper.dtoToEntity(creds)).isEmpty())
			throw new NotAuthorizedException("Username & password do not match (or user does not exist).");
	}

	@Override
    public List<TweetResponseDto> getUserMentions(String username) {
		User incomingUser = new User();
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user.getCredentials().getUsername().equals(username)) {
				incomingUser = user;
			}
		}
		List<Tweet> allTweets = tweetRepository.findAll(); 
		List<Tweet> tweetsToReturn = new ArrayList<Tweet>();
		for (Tweet tweet : allTweets) {
			if(tweet.getMentionedUsers().contains(incomingUser)&& !tweet.isDeleted()) {
				tweetsToReturn.add(tweet);
			}
		}
		return tweetMapper.entitiesToDtos(tweetsToReturn);
    }	
	@Override
	public UserResponseDto getUserByName(String username) {
		User incomingUser = new User();
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user.getCredentials().getUsername().equals(username)) {
				incomingUser = user;
			}
		}
		return userMapper.entityToDto(incomingUser);
	}
}