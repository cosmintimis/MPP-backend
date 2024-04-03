package org.example.serverapp.controller;

import lombok.AllArgsConstructor;
import org.example.serverapp.dto.UserDto;
import org.example.serverapp.entity.User;
import org.example.serverapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false, defaultValue = "false") String sorted) {
        if(sorted.equals("false"))
            return ResponseEntity.ok(userService.getAllUsers());
        else
            return ResponseEntity.ok(userService.getAllUsersSorted());

    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " deleted successfully");
    }


}