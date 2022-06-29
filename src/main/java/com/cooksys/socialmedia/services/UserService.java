package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;

public interface UserService {

	List<User> getAllActiveUsers();
    List<UserResponseDto> getAllActiveUserResponseDtos();
    User getUserByCredentials(CredentialsDto creds) throws NotFoundException;
    void validateCredentials(CredentialsDto creds) throws NotAuthorizedException;

    User updateUser(User user);
    List<User> updateUsers(List<User> results);
}
