package com.auth.Auth.App.controller;

import com.auth.Auth.App.Dto.UserDto;
import com.auth.Auth.App.service.Impl.AuthenticationServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")


public class AuthenticationController {

   @Autowired
    private  AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = authenticationServiceImpl.registerUser(userDto);
        return ResponseEntity.ok(registeredUser);
    }


}
