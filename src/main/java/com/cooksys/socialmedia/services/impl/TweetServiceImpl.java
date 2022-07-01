package com.cooksys.socialmedia.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cooksys.socialmedia.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserService userService;
    private final HashtagService hashtagService;
    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;

    public interface TweetProps {
        // doesn't need to return 'cuz it just mutates
        void setupTweetEntity(Tweet entity);
    }

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
    public List<TweetResponseDto> getUserTweets(String username) {
        List<Tweet> tweets = getAllTweets();
        List<Tweet> userTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().getCredentials().getUsername().toLowerCase().equals(username.toLowerCase()) && !tweet.isDeleted())
                userTweets.add(tweet);
        }
        return tweetMapper.entitiesToDtos(userTweets);
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

    public List<UserResponseDto> getLikedByUsers(Long id) {
        Tweet incomingTweet = getTweetById(id);
        return userMapper.entitiesToDtos(incomingTweet.getLikedByUsers());
    }

    @Override
    public List<HashtagDto> getTags(Long id) {
        Tweet incomingTweet = getTweetById(id);
        return hashtagMapper.entitiesToDtos(incomingTweet.getHashtags());
    }

    @Override
    public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
        Optional<Tweet> tweetToDelete = tweetRepository.findById(id);

        if (tweetToDelete.isEmpty() || tweetToDelete.get().isDeleted())
            throw new NotFoundException("Tweet not found.");

        if (!tweetToDelete.get()
                .getAuthor()
                .getCredentials()
                .getUsername()
                .equals(credentialsDto.getUsername()))
            throw new NotAuthorizedException("You can only delete a tweet that you wrote.");

        tweetToDelete.get().setDeleted(true);
        tweetRepository.saveAndFlush(tweetToDelete.get());

        return tweetMapper.entityToDto(tweetToDelete.get());
    }

    public TweetResponseDto createTweet(TweetProps tweetProps, TweetRequestDto tweetReqDto) throws BadRequestException {
        Tweet postedTweet = tweetMapper.requestDtoToEntity(tweetReqDto);

        postedTweet.setAuthor(userService.getUserByCredentials(tweetReqDto.getCredentials()));
        tweetProps.setupTweetEntity(postedTweet);

        Tweet insertedTweet = tweetRepository.saveAndFlush(postedTweet);

        if (postedTweet.getContent() != null) {
            parseForUserMentions(insertedTweet);
            parseForHashtags(insertedTweet);
        }

        return tweetMapper.entityToDto(insertedTweet);
    }

    @Override
    public List<UserResponseDto> getMentionedUsers(Long id) {
        Tweet incomingTweet = getTweetById(id);
        return userMapper.entitiesToDtos(incomingTweet.getMentionedUsers());
    }


    @Override
    public void parseForUserMentions(Tweet tweet) {
        Pattern pattern = Pattern.compile("@\\w+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tweet.getContent());
        List<User> usersMentioned = new ArrayList<>();
        matcher.results()
                .forEach(matchResult -> {
                    String username = matchResult.group().substring(1);
                    userService.getAllActiveUsers()
                            .stream()
                            .filter(user -> user.getCredentials().getUsername().equals(username))
                            .forEach(user -> {
                                usersMentioned.add(user);
                                user.getMentionedTweets().add(tweet);
                            });
                });

        tweet.getMentionedUsers().addAll(usersMentioned);
        tweetRepository.saveAndFlush(tweet);
        userService.updateUsers(usersMentioned); // .saveAllAndFlush() to reduce database calls
    }

    @Override
    public void parseForHashtags(Tweet tweet) {
        Pattern pattern = Pattern.compile("#\\w+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tweet.getContent());
        List<Hashtag> newHashtagsToAdd = new ArrayList<>();
        matcher.results()
                .forEach(matchResult -> {
                    String hashtagUsed = matchResult.group();
                    Hashtag resultantHashtag = hashtagService.getByLabel(hashtagUsed);
                    if(resultantHashtag == null)
                        resultantHashtag = hashtagService.addNewTag(new Hashtag(hashtagUsed.toLowerCase()));

                    tweet.getHashtags().add(resultantHashtag);
                    resultantHashtag.getTweets().add(tweet);
                });

        tweetRepository.saveAndFlush(tweet);
    }

	@Override
	public ContextDto getContext(Long id) {
		ContextDto contextDto = new ContextDto();
		Tweet contextTweet = getTweetById(id);
		if(contextTweet.isDeleted() || contextTweet.equals(null)) {
			throw new NotFoundException("Not Found");
		}
		Tweet beforeTweet = contextTweet.getInReplyTo();
		List<Tweet> before = new ArrayList<Tweet>();
		List<Tweet> after = contextTweet.getReplies();
		List<Tweet> afterTweets = new ArrayList<Tweet>();
		afterTweets.addAll(after);
		while (beforeTweet != null) {
			before.add(beforeTweet);
			beforeTweet = beforeTweet.getInReplyTo();
		}
		for(Tweet tweet : after) {
			if(tweet.getReplies() != null) {
				afterTweets.addAll(tweet.getReplies());
			}
		}
		for (Tweet tweet : before) {
			if (tweet.isDeleted()) {
				before.remove(tweet);
			}
		}
		for (Tweet tweet : after) {
			if (tweet.isDeleted()) {
				after.remove(tweet);
			}
		}
		contextDto.setBefore(tweetMapper.entitiesToDtos(before));
		contextDto.setTarget(tweetMapper.entityToDto(contextTweet));
		contextDto.setAfter(tweetMapper.entitiesToDtos(afterTweets));
		return contextDto;
	}

    @Override
    public void likeTweet(Tweet tweet, User user) {
        tweet.getLikedByUsers().add(user);
        tweetRepository.saveAndFlush(tweet);
    }
}
