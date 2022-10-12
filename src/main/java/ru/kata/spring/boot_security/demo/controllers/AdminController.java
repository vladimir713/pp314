package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

/**
 * @author Vladimir Chugunov
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

//    @Autowired
//    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.index());
        return "index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User person) {
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User person, BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            System.out.println("Ошибка -----------------------------------------------------------");
            return "new";
        }
        if (!userService.save(person)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "new";
        }
        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") int id) {
        User user = userService.show(id);
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
