package com.cooksys.socialmedia.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final ValidateService validateService;


    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.isDeleted())
                .toList();
    }


    @Override
    public List<UserResponseDto> getAllActiveUserDtos() {
        return userMapper.entitiesToDtos(getAllActiveUsers());
    }

    // on success, do nothing for now
    @Override
    public void validateCredentials(CredentialsDto creds) throws NotAuthorizedException {
        if (userRepository.findByCredentials(credentialsMapper.dtoToEntity(creds)).isEmpty())
            throw new NotAuthorizedException("Username & password do not match (or user does not exist).");
    }

    @Override
    public List<TweetResponseDto> getUserMentions(String username) {
        User incomingUser = new User();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCredentials().getUsername().equals(username)) {
                incomingUser = user;
            }
        }
        List<Tweet> allTweets = tweetRepository.findAll();
        List<Tweet> tweetsToReturn = new ArrayList<Tweet>();
        for (Tweet tweet : allTweets) {
            if (tweet.getMentionedUsers().contains(incomingUser) && !tweet.isDeleted()) {
                tweetsToReturn.add(tweet);
            }
        }
        return tweetMapper.entitiesToDtos(tweetsToReturn);
    }

    @Override
    public UserResponseDto getUserByName(String username) {
        User incomingUser = new User();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCredentials().getUsername().equals(username)) {
                incomingUser = user;
            }
        }
        return userMapper.entityToDto(incomingUser);
    }

    @Override
    public User getUserByCredentials(CredentialsDto creds) throws NotFoundException {
        Optional<User> user = userRepository.findByCredentials(credentialsMapper.dtoToEntity(creds));
        if(user.isEmpty())
            throw new NotFoundException("User not found with that username/password.");

        return user.get();
    }


    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User incomingUser = userMapper.requestDtoToEntity(userRequestDto);
        if (!validateService.isUserNameAvailable(incomingUser.getCredentials().getUsername())) {
            throw new NotAuthorizedException("Username not available.");
        }
        if (incomingUser.getCredentials().getUsername() == null || incomingUser.getCredentials().getPassword() == null
                || incomingUser.getProfile().getEmail() == null) {
            throw new BadRequestException("Must have Username, Password, and Email.");
        }
        if (incomingUser.isDeleted()) {
            incomingUser.setDeleted(false);
        }
        return userMapper.entityToDto(userRepository.saveAndFlush(incomingUser));
    }

    @Override
    public List<UserResponseDto> getUserFollowing(String username) {
        if (!validateService.doesUsernameExist(username)) {
            throw new NotFoundException("User not found.");
        }
        List<User> allUsers = userRepository.findAll();
        User incomingUser = new User();
        for (User user : allUsers) {
            if (user.getCredentials().getUsername().toLowerCase().equals(username.toLowerCase())) {
                incomingUser = user;
            }
            if (incomingUser.isDeleted()) {
                throw new NotAuthorizedException("Unable to get User. Deleted");
            }
        }
        return userMapper.entitiesToDtos(incomingUser.getFollowing());
    }

    @Override
    public List<UserResponseDto> getUserFollowers(String username) {
        List<User> allUsers = userRepository.findAll();
        User incomingUser = new User();
        for (User user : allUsers) {
            if (user.getCredentials().getUsername().toLowerCase().equals(username.toLowerCase())) {
                incomingUser = user;
            }
            if (incomingUser.isDeleted()) {
                throw new NotAuthorizedException("Unable to get User. Deleted");
            }
        }
        return userMapper.entitiesToDtos(incomingUser.getFollowers());
    }


    @Override
    public List<User> updateUsers(List<User> users) {
        return userRepository.saveAllAndFlush(users);
    }
}