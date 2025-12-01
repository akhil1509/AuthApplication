package com.auth.Auth.App.service;

import com.auth.Auth.App.Dto.UserDto;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public  Iterable<UserDto> getAllUsers();

    public UserDto getUserByEmail(String email);

    public UserDto getUserById(String id);

    public  UserDto updateUser(String id, UserDto userDto);
    public String deleteUser(String id);


}
