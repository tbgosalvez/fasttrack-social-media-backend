package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;

public interface HashtagService {

    List<Hashtag> getAllTags();

    List<HashtagDto> getAllTagDtos();

    Hashtag getByLabel(String label);

    List<Hashtag> addNewTags(List<Hashtag> newTags);

    Hashtag addNewTag(Hashtag newTag);

    List<TweetResponseDto> getTweetsWithTag(String label);
}

