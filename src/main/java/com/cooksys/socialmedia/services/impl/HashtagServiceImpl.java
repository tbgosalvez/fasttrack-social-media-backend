package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public List<Hashtag> getAllTags() {
        return hashtagRepository.findAll();
    }

    @Override
    public List<HashtagDto> getAllTagDtos() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public List<Hashtag> addNewTags(List<Hashtag> newTags) {
        return hashtagRepository.saveAllAndFlush(newTags);
    }

    @Override
    public Hashtag addNewTag(Hashtag newTag) {
        return hashtagRepository.saveAndFlush(newTag);
    }
}