package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

//    @PostMapping("/login/{id}")
//    public String loginId(@PathVariable("id") String username, @ModelAttribute("user") User user) {
//        return "redirect:/admin";
//    }


//    @GetMapping("/")
    public String adminPanel(Model model, Principal p) {
        User showUser = userService.findByUsername(p.getName());
        model.addAttribute("user", showUser);
        return "main";
    }
}
