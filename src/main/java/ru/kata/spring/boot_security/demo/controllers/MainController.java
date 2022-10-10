package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping("/")
    public String homePage() {
        return "news";
    }
    @PostMapping("/profile")
    public String pageOfAuthUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(a.getName());
        return "user";
    }
}
