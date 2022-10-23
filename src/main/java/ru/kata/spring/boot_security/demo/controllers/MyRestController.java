package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
public class MyRestController {

    private UserService userService;

    @Autowired
    public MyRestController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.index();
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable("id") int id) {
        return userService.show(id);
    }

    @PostMapping("/users")
        public User addNewUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

}
