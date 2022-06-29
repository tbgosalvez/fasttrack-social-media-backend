package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;

public interface UserService {

	List<UserResponseDto> getAllActiveUsers();

    void validateCredentials(CredentialsDto creds) throws NotAuthorizedException;
}
