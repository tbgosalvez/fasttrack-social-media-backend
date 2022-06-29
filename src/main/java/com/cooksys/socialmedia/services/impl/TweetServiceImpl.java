package com.cooksys.socialmedia.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<Tweet> tweets = tweetRepository.findAll();
		List<Tweet> activeTweets = new ArrayList<Tweet>();
		for (Tweet tweet : tweets) {
			if (!tweet.isDeleted()) {
				activeTweets.add(tweet);
			}
		}
		activeTweets.sort(Comparator.comparing(Tweet::getPosted));
		return tweetMapper.entitiesToDtos(activeTweets);
	}

	@Override
	public TweetResponseDto getTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);
		if(optionalTweet.isEmpty()) {
			throw new NotFoundException("Tweet not found.");
		}
		if(optionalTweet.get().isDeleted()) {
			throw new NotAuthorizedException("Unable to get Tweet. Deleted");
		}
		return tweetMapper.entityToDto(optionalTweet.get());
	}
}
