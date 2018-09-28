package com.sinners.demo.controller;

import com.sinners.demo.service.UserService;
import com.sinners.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    private static final String ACTIVATED_ACCOUNT = "You successfully activated ur fucking account! Now u can add a lot of sins";
    private static final String NOT_ACTIVATED = "Don't try to cheat, your account wasn't activated, or maybe u have already made it";

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        if (!userService.addUser(user)) {
            model.put("message", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String activation(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", ACTIVATED_ACCOUNT);
        } else {
            model.addAttribute("message", NOT_ACTIVATED);
        }
        return "login";
    }
}
