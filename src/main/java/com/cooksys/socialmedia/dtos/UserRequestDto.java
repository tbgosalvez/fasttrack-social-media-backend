package com.cooksys.socialmedia.dtos;

import com.cooksys.socialmedia.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
    private CredentialsDto credentials;
    private ProfileDto profile;
}
