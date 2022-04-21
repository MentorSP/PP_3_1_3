package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        List<User> users = userService.getAllUser();
        model.addAttribute("userList", users);
        return "users";
    }

    @GetMapping("/new")
    public String save(Model model){
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user, String roleUser, String roleAdmin){
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "update";
    }

    @PostMapping("{id}")
    public String update(@PathVariable("id") Long id,@ModelAttribute("user") User user) {
        userService.update(user);
        return ("redirect:/admin");
    }


    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
            userService.delete(id);
        return "redirect:/admin";
    }
}
