package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.*;
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

	void parseForUserMentions(Tweet tweet);

	void parseForHashtags(Tweet tweet);

  List<HashtagDto> getTags(Long id);

  TweetResponseDto createTweet(TweetRequestDto newTweet);
}
