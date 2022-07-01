package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Hashtag getByLabel(String label) {
        Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);
        if (hashtag.isEmpty())
            return null;
        return hashtag.get();
    }

    @Override
    public List<Hashtag> addNewTags(List<Hashtag> newTags) {
        return hashtagRepository.saveAllAndFlush(newTags);
    }

    @Override
    public Hashtag addNewTag(Hashtag newTag) {
        List<Hashtag> currentTags = getAllTags();
        if (!currentTags.contains(newTag))
            return hashtagRepository.saveAndFlush(newTag);

        return currentTags.stream().findFirst().get();
    }
}

