package com.project.MyShop.controllers;

import com.project.MyShop.models.User;
import com.project.MyShop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Principal principal, Model model, User user) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        boolean registrationSuccessful = userService.createUser(user);
        if (!registrationSuccessful) {
            model.addAttribute("user", user);
            model.addAttribute("errorRegistration", "Пользователь уже существует");
            return "registration";
        } else {
            return "redirect:/successfully-rego?email=" + user.getEmail();
        }
    }

    @GetMapping("/successfully-rego")
    public String registerSuccessfully(Principal principal,
                                       @RequestParam(required = false) String email,
                                       Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("email", email);
        return "successfully-rego";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Principal principal, Model model, @PathVariable("code") String code) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (userService.activeUser(code)) {
            model.addAttribute("activate", true);
        } else {
            model.addAttribute("activate", false);
        }
        return "activate";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Principal principal, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }
}
