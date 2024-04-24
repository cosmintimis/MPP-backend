package org.example.serverapp.service;

import com.github.javafaker.Faker;
import org.example.serverapp.dto.UserDto;
import org.example.serverapp.dto.UserListWithSizeDto;
import org.example.serverapp.entity.User;
import org.example.serverapp.repository.UserRepositoryDB;
import org.example.serverapp.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class UserService {
    private final UserRepositoryDB userRepositoryDB;

    @Autowired
    public UserService(UserRepositoryDB userRepositoryDB) {
        this.userRepositoryDB = userRepositoryDB;
    }


    public User addUser(User user) {

        UserValidation.validate(user);
        userRepositoryDB.save(user);
        return user;
    }

    public User getUserById(Integer id) {
        Optional<User> userOptional = userRepositoryDB.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("User with id " + id + " not found");

    }

    public List<User> getAllUsers() {
        return userRepositoryDB.findAll();
    }


    public UserListWithSizeDto getUserListWithSize(String sortedByUsername, String searchByUsername, Integer limit, Integer skip, LocalDate startBirthDate, LocalDate endBirthDate) {
        List<User> users = new ArrayList<>(userRepositoryDB.findAll());
        int size = users.size();

        if(startBirthDate != null && endBirthDate != null){
            users = users.stream().filter(user -> user.getBirthdate().isAfter(startBirthDate.minusDays(1)) &&
                    user.getBirthdate().isBefore(endBirthDate.plusDays(1)))
                    .toList();
            size = users.size();

        }

        if (searchByUsername != null) {
            users = users.stream()
                    .filter(user -> user.getUsername().toLowerCase().contains(searchByUsername.toLowerCase()))
                    .toList();
            size = users.size();
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
        if (limit != null && skip != null) {
            users = users.stream()
                    .skip(skip)
                    .limit(limit)
                    .toList();
        }
        return new UserListWithSizeDto(users, size);
    }


    public User updateUser(Integer id, User updatedUser) {

        Optional<User> userOptional = userRepositoryDB.findById(id);
        if (userOptional.isPresent()) {
            UserValidation.validate(updatedUser);
            User user = userOptional.get() ;

            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            user.setAvatar(updatedUser.getAvatar());
            user.setBirthdate(updatedUser.getBirthdate());
            user.setRating(updatedUser.getRating());
            user.setAddress(updatedUser.getAddress());

            userRepositoryDB.save(user);
            return user;
        }
        throw new RuntimeException("User with id " + id + " not found");


    }

    public void deleteUser(Integer id) {
        if (userRepositoryDB.findById(id).isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        userRepositoryDB.deleteById(id);
    }

    public Map<Integer, Integer> getBirthsPerYear() {
        List<User> users = userRepositoryDB.findAll();

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


    public void generateUsers(int n){


        for (int i = 0; i < n; i++) {
            Faker faker = new Faker();
            User user = User.builder()
                    .username(faker.name().username())
                    .password(faker.internet().password())
                    .email(faker.internet().emailAddress())
                    .avatar(faker.internet().avatar())
                    .birthdate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .rating(faker.number().randomDouble(1, 1, 9))
                    .address(faker.address().fullAddress())
                    .build();
            userRepositoryDB.save(user);
        }
    }
}
