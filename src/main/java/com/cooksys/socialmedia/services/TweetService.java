package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;

public interface TweetService {

	Tweet getTweetById(Long id);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto getTweetResponseById(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

    TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto createTweet(TweetRequestDto tweetReqDto);

	void parseForUserMentions(Tweet tweet);

	List<Hashtag> parseForHashtags(String content);
}
