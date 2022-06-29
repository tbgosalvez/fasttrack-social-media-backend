package com.cooksys.socialmedia.services.impl;

import java.util.List;
import java.util.Optional;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	
	@Override
	public List<User> getAllActiveUsers() {
		return userRepository.findAll()
							.stream()
							.filter(user -> !user.isDeleted())
							.toList();
	}

	@Override
	public List<UserResponseDto> getAllActiveUserResponseDtos() {
		return userMapper.entitiesToDtos(getAllActiveUsers());
	}

	@Override
	public User getUserByCredentials(CredentialsDto creds) throws NotFoundException {
		Optional<User> user = userRepository.findByCredentials(credentialsMapper.dtoToEntity(creds));
		if(user.isEmpty())
			throw new NotFoundException("User not found with that username/password.");

		return user.get();
	}

	// on success, do nothing for now
	@Override
	public void validateCredentials(CredentialsDto creds) throws NotAuthorizedException {
		if(userRepository.findByCredentials(credentialsMapper.dtoToEntity(creds)).isEmpty())
			throw new NotAuthorizedException("Username & password do not match (or user does not exist).");
	}

	@Override
	public User updateUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> updateUsers(List<User> results) {
		return userRepository.saveAllAndFlush(results);
	}
}