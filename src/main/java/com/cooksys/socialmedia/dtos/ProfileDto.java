package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String email;
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> phone;
}
