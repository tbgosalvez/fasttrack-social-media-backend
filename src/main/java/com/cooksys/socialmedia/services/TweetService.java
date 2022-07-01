package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.services.impl.TweetServiceImpl;

import java.util.List;

public interface TweetService {

	Tweet getTweetById(Long id);

	List<TweetResponseDto> getAllTweetResponseDtos();

	List<Tweet> getAllTweets();

	List<TweetResponseDto> getUserTweets(String username);

	TweetResponseDto getTweetResponseById(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

	List<UserResponseDto> getMentionedUsers(Long id);

	List<UserResponseDto> getLikedByUsers(Long id);

	void parseForUserMentions(Tweet tweet);

	void parseForHashtags(Tweet tweet);

	List<HashtagDto> getTags(Long id);

	TweetResponseDto createTweet(TweetServiceImpl.TweetProps tweetSetup, TweetRequestDto newTweet) throws BadRequestException;

	ContextDto getContext(Long id);

	TweetResponseDto createReplyTweet(TweetRequestDto reqTweet);
}
