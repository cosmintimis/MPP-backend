package org.example.serverapp.service;

import org.example.serverapp.dto.UserDto;
import org.example.serverapp.entity.User;
import org.example.serverapp.mapper.UserMapper;
import org.example.serverapp.repository.UserRepository;
import org.example.serverapp.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public List<UserDto> getAllUsers(String sortedByUsername, String searchByUsername, Integer limit, Integer skip) {
        List<User> users = new ArrayList<>(userRepository.getAll());
        if (searchByUsername != null) {
            users = users.stream()
                    .filter(user -> user.getUsername().toLowerCase().contains(searchByUsername.toLowerCase()))
                    .toList();
        }
        if (limit != null && skip != null) {
            users = users.stream()
                    .skip(skip)
                    .limit(limit)
                    .toList();
        }
        if (sortedByUsername != null) {
            if(sortedByUsername.equals("ascending"))
                users = users.stream()
                        .sorted(Comparator.comparing(User::getUsername))
                        .toList();
            else if(sortedByUsername.equals("descending"))
                users = users.stream()
                        .sorted(Comparator.comparing(User::getUsername).reversed())
                        .toList();
        }
        return users.stream()
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

    public Map<Integer, Integer> getBirthsPerYear() {
        List<User> users = userRepository.getAll();

        Map<Integer, Integer> birthsPerYear = new HashMap<>();

        users = users.stream()
                .sorted(Comparator.comparing(User::getBirthdate))
                .toList();

        for (User user : users) {
            int year = user.getBirthdate().getYear();
            if(birthsPerYear.containsKey(year)){
                birthsPerYear.put(year, birthsPerYear.get(year) + 1);
            } else {
                birthsPerYear.put(year, 1);
            }
        }

        return birthsPerYear;

    }
}
