package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
