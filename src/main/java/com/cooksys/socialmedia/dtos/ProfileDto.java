package com.cooksys.socialmedia.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileDto {
    private String email;
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> phone;
}
