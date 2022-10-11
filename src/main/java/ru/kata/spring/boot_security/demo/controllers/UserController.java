package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/edit")
    public String update(User user) {
        userService.save(user);
        return "redirect:/user";
    }
    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        return "edit";
    }
    @GetMapping
    public String show(Principal p, Model model) {
        User showUser = userService.findByUsername(p.getName());
        model.addAttribute("user", showUser);
        return "user";
    }
}
