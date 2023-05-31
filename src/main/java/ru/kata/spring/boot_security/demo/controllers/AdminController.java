package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entitys.Role;
import ru.kata.spring.boot_security.demo.entitys.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/admin/addUserForm")
    public String addUserForm(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "newUser";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") Set<Role> roles) {
        user.setRoleSet(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("userToUpdate", user);
        model.addAttribute("roles", roleService.findAll());
        return "updateUser";
    }
    @PatchMapping("/admin/updateUser")
    public String updateUser(User user, @RequestParam("roles") Set<Role> roles) {
        user.setRoleSet(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}