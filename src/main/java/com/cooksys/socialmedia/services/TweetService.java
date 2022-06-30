package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;

import java.util.List;

public interface TweetService {

	Tweet getTweetById(Long id);

	List<TweetResponseDto> getAllTweetResponseDtos();

	List<Tweet> getAllTweets();

	TweetResponseDto getTweetResponseById(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

  TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

	List<UserResponseDto> getMentionedUsers(Long id);

	List<UserResponseDto> getLikedByUsers(Long id);

	List<HashtagDto> getTags(Long id);
}
