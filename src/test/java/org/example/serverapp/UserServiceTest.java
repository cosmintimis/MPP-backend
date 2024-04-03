package org.example.serverapp;

import org.example.serverapp.dto.UserDto;
import org.example.serverapp.entity.User;
import org.example.serverapp.repository.UserRepository;
import org.example.serverapp.service.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserServiceTest {
    @Test
    public void testAddUser() {
        final UserRepository userRepository = new UserRepository();
        final UserService userService = new UserService(userRepository);

        int currentSize = userService.getAllUsers().size();

        UserDto newUserDto = new UserDto(userRepository.firstFreeId(), "test", "test", "test", "test", null, 0.0, "test");
        try {
            userService.addUser(newUserDto);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        UserDto goodUserDto = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "testing123",
                "t@t.com",
                "test",
                LocalDate.of(2024, 1, 1),
                1.7,
                "address test"
        );

        try {
            userService.addUser(goodUserDto);
            assert true;
        } catch (Exception e) {
            assert false;
        }

        assert userService.getAllUsers().size() == currentSize + 1;


        UserDto invalidUsername = new UserDto(
                userRepository.firstFreeId(),
                "",
                "testing123",
                "t@t.com",
                "test",
                LocalDate.of(2024, 1, 1),
                1.7,
                "address test");
        UserDto invalidPassword = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "",
                "t@t.com",
                "test",
                LocalDate.of(2024, 1, 1),
                1.7,
                "address test");
        UserDto invalidEmail = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "testing123",
                "",
                "test",
                LocalDate.of(2024, 1, 1),
                1.7,
                "address test");
        UserDto invalidAvatar = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "testing123",
                "t@t.com",
                "",
                LocalDate.of(2024, 1, 1),
                1.7,
                "address test");
        UserDto invalidBirthdate = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "testing123",
                "t@t.com",
                "test",
                null,
                1.7,
                "address test");
        UserDto invalidRating = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "testing123",
                "t@t.com",
                "test",
                LocalDate.of(2024, 1, 1),
                11.0,
                "address test");
        UserDto invalidAddress = new UserDto(
                userRepository.firstFreeId(),
                "cosmin alexandru",
                "testing123",
                "t@t.com",
                "test",
                LocalDate.of(2024, 1, 1),
                1.7,
                "");

        try {
            userService.addUser(invalidUsername);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            userService.addUser(invalidPassword);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            userService.addUser(invalidEmail);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            userService.addUser(invalidAvatar);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            userService.addUser(invalidBirthdate);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            userService.addUser(invalidRating);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            userService.addUser(invalidAddress);
            assert false;
        } catch (Exception e) {
            assert true;
        }

    }

    @Test
    public void testGetUserById() {
        final UserRepository userRepository = new UserRepository();
        final UserService userService = new UserService(userRepository);

        UserDto firstUser = userService.getUserById(1);

        assert firstUser.getId() == 1;
        assert firstUser.getUsername().equals("Cosmin Timis");
        assert firstUser.getPassword().equals("parolaaiabuna");
        assert firstUser.getEmail().equals("cosmin.timis@gmail.com");
        assert firstUser.getAvatar().equals("https://robohash.org/e5a84795597420d98d606433f8ad1f70?set=set4&bgset=&size=400x400");
        assert firstUser.getBirthdate().equals(LocalDate.parse("2003-01-01"));
        assert firstUser.getRating() == 8.8;
        assert firstUser.getAddress().equals("address1");

        try {
            userService.getUserById(100);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void testGetAllUsers() {
        final UserRepository userRepository = new UserRepository();
        final UserService userService = new UserService(userRepository);

        assert userService.getAllUsers().size() == 11;
    }

    @Test
    public void testDeleteUser() {
        final UserRepository userRepository = new UserRepository();
        final UserService userService = new UserService(userRepository);

        int currentSize = userService.getAllUsers().size();

        try {
            userService.deleteUser(100);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        userService.deleteUser(1);

        assert userService.getAllUsers().size() == currentSize - 1;
    }

    @Test
    public void testUpdateUser() {
        final UserRepository userRepository = new UserRepository();
        final UserService userService = new UserService(userRepository);

        UserDto firstUser = userService.getUserById(1);

        UserDto updateUserInvalidUsername = new UserDto(
                firstUser.getId(),
                "",
                firstUser.getPassword(),
                firstUser.getEmail(),
                firstUser.getAvatar(),
                firstUser.getBirthdate(),
                firstUser.getRating(),
                firstUser.getAddress()
        );
        UserDto updateUserInvalidPassword = new UserDto(
                firstUser.getId(),
                firstUser.getUsername(),
                "",
                firstUser.getEmail(),
                firstUser.getAvatar(),
                firstUser.getBirthdate(),
                firstUser.getRating(),
                firstUser.getAddress()
        );
        UserDto updateUserInvalidEmail = new UserDto(
                firstUser.getId(),
                firstUser.getUsername(),
                firstUser.getPassword(),
                "",
                firstUser.getAvatar(),
                firstUser.getBirthdate(),
                firstUser.getRating(),
                firstUser.getAddress()
        );
        UserDto updateUserInvalidAvatar = new UserDto(
                firstUser.getId(),
                firstUser.getUsername(),
                firstUser.getPassword(),
                firstUser.getEmail(),
                "",
                firstUser.getBirthdate(),
                firstUser.getRating(),
                firstUser.getAddress()
        );
        UserDto updateUserInvalidBirthdate = new UserDto(
                firstUser.getId(),
                firstUser.getUsername(),
                firstUser.getPassword(),
                firstUser.getEmail(),
                firstUser.getAvatar(),
                null,
                firstUser.getRating(),
                firstUser.getAddress()
        );
        UserDto updateUserInvalidRating = new UserDto(
                firstUser.getId(),
                firstUser.getUsername(),
                firstUser.getPassword(),
                firstUser.getEmail(),
                firstUser.getAvatar(),
                firstUser.getBirthdate(),
                11.0,
                firstUser.getAddress()
        );
        UserDto updateUserInvalidAddress = new UserDto(
                firstUser.getId(),
                firstUser.getUsername(),
                firstUser.getPassword(),
                firstUser.getEmail(),
                firstUser.getAvatar(),
                firstUser.getBirthdate(),
                firstUser.getRating(),
                ""
        );

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidUsername);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidPassword);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidEmail);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidAvatar);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidBirthdate);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidRating);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        try {
            userService.updateUser(firstUser.getId(), updateUserInvalidAddress);
            assert false;
        } catch (Exception e) {
            assert true;
        }

        UserDto updateUser = new UserDto(
                firstUser.getId(),
                "alexandru horj",
                "parolabuna",
                "test@gmail.ro",
                "avatarnou",
                LocalDate.of(2000, 1, 1),
                9.9,
                "adresanoua");

        try {
            userService.updateUser(firstUser.getId(), updateUser);
            assert true;
        } catch (Exception e) {
            assert false;
        }

        UserDto firstUserUpdated = userService.getUserById(1);

        assert firstUserUpdated.getUsername().equals("alexandru horj");
        assert firstUserUpdated.getPassword().equals("parolabuna");
        assert firstUserUpdated.getEmail().equals("test@gmail.ro");
        assert firstUserUpdated.getAvatar().equals("avatarnou");
        assert firstUserUpdated.getBirthdate().equals(LocalDate.of(2000, 1, 1));
        assert firstUserUpdated.getRating() == 9.9;
        assert firstUserUpdated.getAddress().equals("adresanoua");


    }

}
