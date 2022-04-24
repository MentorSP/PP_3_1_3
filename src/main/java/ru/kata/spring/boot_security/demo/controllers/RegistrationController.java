package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("начинает работать метод public String registration");
        String roleUser = null;
        String roleAdmin = null;
        model.addAttribute("userForm", new User());
        model.addAttribute("roleUser", roleUser);
        model.addAttribute("roleAdmin", roleAdmin);

        return "redirect:registration";
    }
}