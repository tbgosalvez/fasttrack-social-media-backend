package com.cooksys.socialmedia.services;

public interface ValidateService {

    boolean doesHashtagExist(String label);

    boolean doesUsernameExist(String username);

    boolean isUserNameAvailable(String username);

}
