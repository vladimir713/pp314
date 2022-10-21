package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

/**
 * @author Vladimir Chugunov
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, Principal p) {
        System.out.println("get - /admin - main");
        User principalUser = userService.findByUsername(p.getName());
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        model.addAttribute("principalUser", principalUser);
        model.addAttribute("roles", userService.listRoles());
        model.addAttribute("users", userService.index());
        return "main";
    }

//    @GetMapping("/new")
//    public String newPerson(@ModelAttribute("user") User person) {
//        return "new";
//    }

    @GetMapping("/new")
    public String newUserPanel(Model model, Principal p) {
        System.out.println("get /admin/new - newUser");
        model.addAttribute("principalUser", userService.findByUsername(p.getName()));
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.listRoles());
        return "newUser";
    }
    @PostMapping("/new")
    public String create(@ModelAttribute("user") User person, BindingResult bindingResult, Model model ) {
        System.out.println("post - /admin - /admin");
        if (bindingResult.hasErrors()) {
            return "new";
        }
        if (!userService.save(person)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "new";
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
//    public String update(@PathVariable("id") int id, @ModelAttribute("user") User newUser) {
        public String find(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
//        User userOld = userService.show(id);
//        newUser.setPassword(userOld.getPassword());
//        newUser.setRoles(userOld.getRoles());
//        userService.update(newUser);
        return "main";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        System.out.println("update------------------------------------");
        userService.update(user);
        return "redirect:/admin";
    }
//    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        User user = userService.show(id);
        model.addAttribute("user", user);
        return "edit";
    }

//    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
