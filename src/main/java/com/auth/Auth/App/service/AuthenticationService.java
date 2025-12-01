package com.auth.Auth.App.service;

import com.auth.Auth.App.Dto.UserDto;

public interface AuthenticationService {
    public UserDto registerUser(UserDto userDto);
}
