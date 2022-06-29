package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })

public interface UserMapper {
    
    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(User user);
	
	User responseDtoToEntity(UserResponseDto userResponseDto);
	
	User requestDtoToEntity(UserRequestDto userRequestDto);
	
	List<UserResponseDto> entitiesToDtos(List<User> users);
}
