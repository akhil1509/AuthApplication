package com.auth.Auth.App.service.Impl;

import com.auth.Auth.App.Dto.UserDto;
import com.auth.Auth.App.exception.IllegalArgumentException;
import com.auth.Auth.App.exception.ResourceNotFoundException;
import com.auth.Auth.App.helper.UUIDHelper;
import com.auth.Auth.App.model.Provider;
import com.auth.Auth.App.model.User;
import com.auth.Auth.App.repository.UserRepository;
import com.auth.Auth.App.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service

public class UserServiceImpl implements UserService {


    private  UserRepository userRepository;
    private  ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {

        if (userDto.getEmail() == null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);
        User saved = userRepository.save(user);
        UserDto dto = modelMapper.map(saved, UserDto.class);
        return dto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User is not exist with email: " + email));
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }

    @Override
    public UserDto getUserById(String id) {
        UUID uuid = UUIDHelper.generateUUID(id);
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found - " + id));
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        UUID uuid = UUIDHelper.generateUUID(id);
        User existingUser = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found - " + id));
        // we are not updating email
        if (userDto.getName()!=null)existingUser.setName(userDto.getName());
        if (userDto.getGender()!=null)existingUser.setGender(userDto.getGender());
        if (userDto.getImage()!=null)existingUser.setImage(userDto.getImage());
        if (userDto.getProvider()!=null)existingUser.setProvider(userDto.getProvider());
        //todo passqword change
        if (userDto.getPassword()!=null)existingUser.setPassword(userDto.getPassword());
        existingUser.setEnabled(userDto.isEnabled());
        existingUser.setUpdatedAt(Instant.now());
        User saved = userRepository.save(existingUser);
        UserDto dto = modelMapper.map(saved, UserDto.class);
        return dto;
    }

    @Transactional
    @Override
    public String deleteUser(String id) {
        UUID uuid = UUIDHelper.generateUUID(id);
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found - " + id));
        userRepository.delete(user);

        return "User deleted successfully - " + id;
    }

    @Transactional
    @Override
    public Iterable<UserDto> getAllUsers() {
      return  userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();

    }
}
