package org.example.serverapp.controller;

import lombok.AllArgsConstructor;
import org.example.serverapp.dto.UserDto;
import org.example.serverapp.dto.UserListWithSizeDto;
import org.example.serverapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Map;

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
    public ResponseEntity<UserListWithSizeDto> getAllUsers(@RequestParam(required = false) String sortedByUsername,
                                                           @RequestParam(required = false) String searchByUsername,
                                                           @RequestParam(required = false) Integer limit,
                                                           @RequestParam(required = false) Integer skip,
                                                           @RequestParam(required = false) LocalDate startBirthDate,
                                                           @RequestParam(required = false) LocalDate endBirthDate
                                                           ){

        if(sortedByUsername != null && !sortedByUsername.isEmpty() && (!sortedByUsername.equals("ascending") && !sortedByUsername.equals("descending"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sortedByUsername parameter");
        }

        if(limit != null && skip == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If limit is provided, skip must be provided too");
        }

        if(limit == null && skip != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If skip is provided, limit must be provided too");
        }

        if(limit != null && (limit < 0 || skip < 0 || limit > 1000)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid limit or skip parameter");
        }

        if(startBirthDate == null && endBirthDate != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If endBirthDate is provided, startBirthDate must be provided too");
        }

        if(startBirthDate != null && endBirthDate == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If startBirthDate is provided, endBirthDate must be provided too");
        }

        return ResponseEntity.ok(userService.getUserListWithSize(sortedByUsername, searchByUsername, limit, skip, startBirthDate, endBirthDate));

    }

    @GetMapping("/births-per-year")
    public ResponseEntity<Map<Integer, Integer>> getBirthsPerYear() {
        return ResponseEntity.ok(userService.getBirthsPerYear());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto updatedUser) {
        /// handle bad request and not found id
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " deleted successfully");
    }


}