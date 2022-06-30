package com.cooksys.socialmedia.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;
	private final UserMapper userMapper;
	private final HashtagMapper hashtagMapper;

	@Override
	public Tweet getTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);
		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("Tweet not found.");
		}
		if (optionalTweet.get().isDeleted()) {
			throw new NotAuthorizedException("Unable to get Tweet. Deleted");
		}

		return optionalTweet.get();
	}

	@Override
	public TweetResponseDto getTweetResponseById(Long id) {
		return tweetMapper.entityToDto(getTweetById(id));
	}

	@Override
	public List<TweetResponseDto> getAllTweetResponseDtos() {
		List<Tweet> activeTweets =
				tweetRepository.findAll()
						.stream()
						.filter(tweet -> !tweet.isDeleted())
						.sorted(Comparator.comparing(Tweet::getPosted))
						.toList();

		return tweetMapper.entitiesToDtos(activeTweets);
	}

	@Override
	public List<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}

	@Override
	public List<TweetResponseDto> getReplies(Long id) {
		Tweet repliedTweet = getTweetById(id);

		List<Tweet> replyTweets =
				tweetRepository.findAll()
						.stream()
						.filter(tweet -> repliedTweet.getReplies().contains(tweet) && !tweet.isDeleted())
						.toList();

		return tweetMapper.entitiesToDtos(replyTweets);
	}

	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		Tweet originalTweet = getTweetById(id);

		List<Tweet> repostedTweets =
				tweetRepository.findAll()
						.stream()
						.filter(tweet -> originalTweet.getReposts().contains(tweet) && !tweet.isDeleted())
						.toList();

		return tweetMapper.entitiesToDtos(repostedTweets);
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
		Optional<Tweet> tweetToDelete = tweetRepository.findById(id);

		if(tweetToDelete.isEmpty() || tweetToDelete.get().isDeleted())
			throw new NotFoundException("Tweet not found.");

		if(!tweetToDelete.get()
				.getAuthor()
				.getCredentials()
				.getUsername()
				.equals(credentialsDto.getUsername()))
			throw new NotAuthorizedException("You can only delete a tweet that you wrote.");

		tweetToDelete.get().setDeleted(true);
		tweetRepository.saveAndFlush(tweetToDelete.get());

		return tweetMapper.entityToDto(tweetToDelete.get());
	}

	@Override
	public List<UserResponseDto> getMentionedUsers(Long id) {
		Tweet incomingTweet = getTweetById(id);
		return userMapper.entitiesToDtos(incomingTweet.getMentionedUsers());
	}
	
	/////////// endpoint to merge /////////////
	
	@Override
	public List<UserResponseDto> getLikedByUsers(Long id) {
		Tweet incomingTweet = getTweetById(id);
		return userMapper.entitiesToDtos(incomingTweet.getLikedByUsers());
	}

	@Override
	public List<HashtagDto> getTags(Long id) {
		Tweet incomingTweet = getTweetById(id);
		return hashtagMapper.entitiesToDtos(incomingTweet.getHashtags());
	}
}
