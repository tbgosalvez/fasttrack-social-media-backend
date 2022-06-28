package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
