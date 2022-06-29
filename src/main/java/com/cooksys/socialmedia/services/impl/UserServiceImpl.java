package com.cooksys.socialmedia.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.UserResponseDto;
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
	
	@Override
	public List<UserResponseDto> getAllActiveUsers() {
		List<User> users = userRepository.findAll();
		List<User> activeUsers = new ArrayList<User>();
		for(User user : users) {
			if(!user.isDeleted()) {
				activeUsers.add(user);
			}
		}
		return userMapper.entitiesToDtos(activeUsers);
	}
}