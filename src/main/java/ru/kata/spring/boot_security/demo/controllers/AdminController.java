package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        model.addAttribute("rolesList", userService.getAllRoles());
        model.addAttribute("user", new User());
        model.addAttribute("authorisedUser", (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        return "admin";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User userToEdit = userService.findById(id);
        String roleUser = (userToEdit.getStringRoles().contains("ROLE_USER") ? "on" : null);
        String roleAdmin = (userToEdit.getStringRoles().contains("ROLE_ADMIN") ? "on" : null);

        model.addAttribute("userToEdit", userService.findById(id));
        model.addAttribute("roleUser", roleUser);
        model.addAttribute("roleAdmin", roleAdmin);
        return "admin";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return ("redirect:/admin");
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


//    @PostMapping("/new")
//    public String addNewUser(@ModelAttribute("user") User user, String roleUser, String roleAdmin){
//        userService.save(user);
//        return "redirect:/admin";
//    }
}
