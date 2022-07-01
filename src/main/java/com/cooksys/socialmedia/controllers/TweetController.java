package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;
    //################ TweetMapper not used in this controller #################

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweetResponseDtos();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetResponseById(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
        return tweetService.getReplies(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id) {
        return tweetService.getReposts(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentionedUsers(@PathVariable Long id) {
        return tweetService.getMentionedUsers(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikedByUsers(@PathVariable Long id) {
        return tweetService.getLikedByUsers(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTags(@PathVariable Long id) {
        return tweetService.getTags(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getContext(@PathVariable Long id) {
        return tweetService.getContext(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto newTweet) {
        userService.getUserByCredentials(newTweet.getCredentials());
        return tweetService.createTweet(requestDto -> {
            if (requestDto.getContent().isBlank())
                throw new BadRequestException("Tweet cannot be blank.");
        }, newTweet);
    }

    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createReplyTweet(@PathVariable Long id, @RequestBody TweetRequestDto reqTweet) {
        userService.getUserByCredentials(reqTweet.getCredentials());
        return tweetService.createTweet(tweetEntity -> {
            if (tweetEntity.getContent().isBlank())
                throw new BadRequestException("Tweet cannot be blank.");

            tweetEntity.setInReplyTo(tweetService.getTweetById(id));

        }, reqTweet);
    }

    @PostMapping("/{id}/repost")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createRepostTweet(@PathVariable Long id, @RequestBody CredentialsDto creds) {
        userService.getUserByCredentials(creds);
        return tweetService.createTweet(tweetEntity -> tweetEntity.setRepostOf(tweetService.getTweetById(id)), new TweetRequestDto(null, creds));
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto creds) {
        userService.getUserByCredentials(creds);

        Tweet coolTweet = tweetService.getTweetById(id);
        User coolUser = userService.getUserByCredentials(creds);

        tweetService.likeTweet(coolTweet, coolUser);
        userService.likeTweet(coolTweet, coolUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto creds) {
        userService.getUserByCredentials(creds);
        return tweetService.deleteTweet(id, creds);
    }
}
