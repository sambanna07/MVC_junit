package com.junit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.junit.models.User;
import com.junit.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    /**
     * Get all users
     */
    @GetMapping("/getAll")
    public List<User> getAll() {
        try {
            return userService.getAll();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get user by ID
     */
    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable(value = "id") Long id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get user by name
     */
    @GetMapping("/get/name")
    public User getUserByName(@RequestParam(value = "name") String name) {
        try {
            return userService.getUserByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Add a new user
     */
    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        try {
            return userService.saveUser(user);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Update a user by ID
     */
    @PutMapping("/update/{id}")
    public User updateUserById(@PathVariable(value = "id") Long id, @RequestBody User user) {
        try {
            user.setId(id);
            return userService.updateUser(user);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Delete a user by ID
     */
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            // Handle the exception
        }
    }
}
