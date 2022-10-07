package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

/**
 * @author Vladimir Chugunov
 */
@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userService.index());
        return "index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User person) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User person) {
        userService.save(person);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String update(User user) {
        userService.save(user);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/";
    }
}
