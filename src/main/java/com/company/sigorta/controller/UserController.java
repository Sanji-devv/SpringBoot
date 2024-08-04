package com.company.sigorta.controller;

import com.company.sigorta.dto.UserDto;
import com.company.sigorta.model.UserModel;
import com.company.sigorta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    private UserDto convertToDTO(UserModel user) { return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail()); }

    @GetMapping("/list")
    public List<UserDto> getUsers()
    {
        return userService.getUsers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Integer id)
    {
        try
        {
            UserModel user = userService.getUserById(id);
            UserDto userDTO = convertToDTO(user);
            String userInfo = userDTO.toString();
            return ResponseEntity.ok(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<String> getUserByName(@PathVariable String name)
    {
        try
        {
            UserModel user = userService.getUserByName(name);
            UserDto userDTO = convertToDTO(user);
            String userInfo = userDTO.toString();
            return ResponseEntity.ok(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<String> getUserBySurname(@PathVariable String surname)
    {
        try
        {
            UserModel user = userService.getUserBySurname(surname);
            UserDto userDTO = convertToDTO(user);
            String userInfo = userDTO.toString();
            return ResponseEntity.ok(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable String email)
    {
        try
        {
            UserModel user = userService.getUserByEmail(email);
            UserDto userDTO = convertToDTO(user);
            String userInfo = userDTO.toString();
            return ResponseEntity.ok(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserModel userModel)
    {
        try
        {
            UserModel createdUser = userService.createUser(userModel);
            UserDto userDTO = convertToDTO(createdUser);
            String userInfo = userDTO.toString();
            return ResponseEntity.status(201).body(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(400).body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserModel userModel)
    {
        try
        {
            UserModel updatedUser = userService.updateUser(id, userModel);
            UserDto userDTO = convertToDTO(updatedUser);
            String userInfo = userDTO.toString();
            return ResponseEntity.ok(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(400).body("Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id)
    {
        try
        {
            UserModel userToDelete = userService.getUserById(id);
            userService.deleteUser(id);
            UserDto userDTO = convertToDTO(userToDelete);
            String userInfo = userDTO.toString();
            return ResponseEntity.ok(userInfo);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(400).body("Error deleting user: " + e.getMessage());
        }
    }
}
