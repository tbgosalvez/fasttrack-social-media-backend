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
    private int id;
    private User author;
    private Timestamp posted;
    private Optional<String> content;
    private Optional<Tweet> inReplyTo;
    private Optional<Tweet> repostOf;
}
