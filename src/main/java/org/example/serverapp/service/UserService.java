package org.example.serverapp.service;

import org.example.serverapp.entity.CreateUser;
import org.example.serverapp.entity.User;
import org.example.serverapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User addUser(CreateUser user) {

        User newUser = new User(getAllUsers().size() + 1, user.getUsername(), user.getPassword(), user.getEmail(), user.getAvatar(), user.getBirthdate(), user.getRating(), user.getAddress());
       // UserValidation.validate(user);
        userRepository.add(newUser);
        return newUser;
    }

    public User getUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        return userRepository.getById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User updateUser(Integer id, User updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }

        // UserValidation.validate(updatedUser);

        userRepository.update(id, updatedUser);

        return userRepository.getById(id);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        userRepository.delete(id);
    }
}
