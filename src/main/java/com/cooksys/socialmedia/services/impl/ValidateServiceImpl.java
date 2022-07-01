package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public boolean doesHashtagExist(String label) {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        for (Hashtag hashtag : hashtags) {
            if ((hashtag.getLabel()).equals(label)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doesUsernameExist(String username) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCredentials().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isUserNameAvailable(String username) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCredentials().getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

}
