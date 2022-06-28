package com.cooksys.socialmedia.dtos;

import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Optional;

@NoArgsConstructor
@Data
public class TweetResponseDto {
    private Long id;
    private UserResponseDto author;
    private Timestamp posted;
    private Optional<String> content;
    private Optional<TweetResponseDto> inReplyTo;
    private Optional<TweetResponseDto> repostOf;
}
