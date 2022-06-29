package com.cooksys.socialmedia.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cooksys.socialmedia.dtos.TweetResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto getTweetById(Long id);

	List<TweetResponseDto> getReplies(Long id);

}
