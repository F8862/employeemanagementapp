package com.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private final String token;

    public UserResponse(String token) {
        this.token = token;
    }
}
