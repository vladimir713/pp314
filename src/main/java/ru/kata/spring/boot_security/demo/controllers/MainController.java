package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "news";
    }
    @PostMapping("/profile")
    public String pageOfAuthUser(Model model) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User u = userService.findByUsername(a.getName());
        model.addAttribute("user", u);
        model.addAttribute("userAuth", a);
        System.out.println(u.getUsername());
        return "redirect:/profile";
    }
    @GetMapping("/profile")
    public String infoOfUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        System.out.println("-----------------------------" + user.getUsername() + "  " + user.getAge() + "---------------------------------------");
        return "user";
    }
}
