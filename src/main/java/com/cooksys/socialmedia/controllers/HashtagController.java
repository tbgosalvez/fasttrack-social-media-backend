package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTweetsWithTag(@PathVariable String label) {
        return hashtagService.getTweetsWithTag(label);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HashtagDto> getAllTags() {
        return hashtagService.getAllTagDtos();
    }


}
