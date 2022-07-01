package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;

import java.util.List;

public interface HashtagService {

    List<Hashtag> getAllTags();

    List<HashtagDto> getAllTagDtos();

    Hashtag getByLabel(String label);

    List<Hashtag> addNewTags(List<Hashtag> newTags);

    Hashtag addNewTag(Hashtag newTag);

    List<TweetResponseDto> getTweetsWithTag(String label, List<Tweet> allTweets);
}

