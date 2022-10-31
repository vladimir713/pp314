package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
public class MyRestController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
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
        public ResponseEntity<User> addNewUser(@RequestBody User user) {
        if (userService.save(user)) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.update(user);
        return user;
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "User with id = " + id + " was deleted";
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/userAuth")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
