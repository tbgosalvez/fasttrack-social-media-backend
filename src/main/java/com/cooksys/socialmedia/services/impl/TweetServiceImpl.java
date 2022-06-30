package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final UserService userService;
	private final HashtagService hashtagService;
	private final TweetMapper tweetMapper;

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
	public List<TweetResponseDto> getAllTweets() {
		List<Tweet> activeTweets =
				tweetRepository.findAll()
						.stream()
						.filter(tweet -> !tweet.isDeleted())
						.sorted(Comparator.comparing(Tweet::getPosted))
						.toList();

		return tweetMapper.entitiesToDtos(activeTweets);
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
	public TweetResponseDto createTweet(TweetRequestDto tweetReqDto) {
		Tweet postedTweet = tweetMapper.requestDtoToEntity(tweetReqDto);

		postedTweet.setAuthor(userService.getUserByCredentials(tweetReqDto.getCredentials()));

		Tweet insertedTweet = tweetRepository.saveAndFlush(postedTweet);
		parseForUserMentions(insertedTweet);

		return tweetMapper.entityToDto(insertedTweet);
	}

	@Override
	public void parseForUserMentions(Tweet tweet) {
		Pattern pattern = Pattern.compile("@\\w+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(tweet.getContent());

		matcher.results()
				.forEach(matchResult -> {
					String username = matchResult.group().substring(1);
					List<User> results = userService.getAllActiveUsers()
							.stream()
							.filter(user -> user.getCredentials().getUsername().equals(username))
							.toList();

					tweet.getMentionedByUsers().addAll(results);
					results.forEach(user -> user.getMentionedByTweets().add(tweet));
					tweetRepository.saveAndFlush(tweet);
					userService.updateUsers(results); // .saveAllAndFlush() to reduce database calls
				});
	}

	@Override
	public void parseForHashtags(Tweet tweet) {
//		Pattern pattern = Pattern.compile("#\\w+", Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(tweet.getContent());
//
//		matcher.results()
//				.forEach(matchResult -> {
//					String hashtag = matchResult.group().substring(1);
//					List<Hashtag> results = hashtagService.getAllHashtags()
//							.stream()
//							.filter(user -> user.getCredentials().getUsername().equals(username))
//							.toList();
//
//					tweet.getMentionedByUsers().addAll(results);
//					results.forEach(user -> user.getMentionedByTweets().add(tweet));
//					tweetRepository.saveAndFlush(tweet);
//					userService.updateUsers(results); // .saveAllAndFlush() to reduce database calls
//				});
//
	}
}
