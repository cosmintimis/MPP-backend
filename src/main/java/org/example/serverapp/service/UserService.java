package org.example.serverapp.service;

import org.example.serverapp.dto.UserDto;
import org.example.serverapp.entity.User;
import org.example.serverapp.mapper.UserMapper;
import org.example.serverapp.repository.UserRepository;
import org.example.serverapp.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDto addUser(UserDto user) {
        User newUser = new User(userRepository.firstFreeId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getAvatar(), user.getBirthdate(), user.getRating(), user.getAddress());
        UserValidation.validate(newUser);
        userRepository.add(newUser);
        return UserMapper.mapToUserDto(newUser);
    }

    public UserDto getUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        return UserMapper.mapToUserDto(userRepository.getById(id));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.getAll().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    public List<UserDto> getAllUsersSorted() {

        List<User> sortedUsers = new ArrayList<>(userRepository.getAll());

        return sortedUsers.stream()
                .sorted(Comparator.comparing(User::getUsername))
                .map(UserMapper::mapToUserDto)
                .toList();
    }


    public UserDto updateUser(Integer id, UserDto updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }

        User user = UserMapper.mapToUser(updatedUser);
        UserValidation.validate(user);
        userRepository.update(id, user);

        return updatedUser;
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        userRepository.delete(id);
    }
}
