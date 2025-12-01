package com.auth.Auth.App.service.Impl;

import com.auth.Auth.App.Dto.UserDto;
import com.auth.Auth.App.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private    UserServiceImpl userServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto registerUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto user = userServiceImpl.createUser(userDto);
        return user;
    }
}
