package com.auth.Auth.App.controller;

import com.auth.Auth.App.Dto.UserDto;
import com.auth.Auth.App.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user = userServiceImpl.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<UserDto>> getAllUsers(){
        Iterable<UserDto> users = userServiceImpl.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail( @PathVariable("email") String email){
        UserDto userByEmail = userServiceImpl.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById( @PathVariable("id") String id){
        UserDto userById = userServiceImpl.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
public  ResponseEntity<UserDto> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto){
    UserDto updateUser = userServiceImpl.updateUser(id, userDto);
    return new ResponseEntity<>(updateUser, HttpStatus.OK);
}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        userServiceImpl.deleteUser(id);
        return new ResponseEntity<String>("deleted successfully",HttpStatus.NO_CONTENT);
    }

}
