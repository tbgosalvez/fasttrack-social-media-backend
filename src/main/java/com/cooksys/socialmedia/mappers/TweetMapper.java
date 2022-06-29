package com.cooksys.socialmedia.mappers;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {
	
    TweetResponseDto entityToDto(Tweet tweet);
    
    Tweet optionalToEntity(Optional<Tweet> tweet);
	
	Tweet responseDtoToEntity(TweetResponseDto tweetResponseDto);
	
	Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);
	
	List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

}
