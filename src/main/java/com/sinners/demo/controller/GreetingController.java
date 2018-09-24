package com.sinners.demo.controller;

import com.sinners.demo.repository.SinRepository;
import com.sinners.demo.sin.Sin;
import com.sinners.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private SinRepository sinRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Map<String, Object> model) {
        if (filter == null || filter.isEmpty()) {
            return getAllSinsAndReturn(model);
        }
        List<Sin> sins = sinRepository.findByType(filter);
        model.put("sins", sins);
        model.put("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addSin(
            @AuthenticationPrincipal User user,
            @RequestParam String sinType,
            @RequestParam Integer sinWeight,
            @RequestParam String sinDescription,
            Map<String, Object> model) {
        Sin sin = new Sin(sinType, sinWeight, sinDescription, user);
        sinRepository.save(sin);
        return getAllSinsAndReturn(model);
    }

    private String getAllSinsAndReturn(Map<String, Object> model) {
        Iterable<Sin> sins = sinRepository.findAll();
        model.put("sins", sins);
        return "main";
    }
}