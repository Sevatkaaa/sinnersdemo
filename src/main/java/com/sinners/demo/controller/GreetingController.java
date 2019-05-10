package com.sinners.demo.controller;

import com.sinners.demo.repository.SinRepository;
import com.sinners.demo.sin.Sin;
import com.sinners.demo.user.Role;
import com.sinners.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String mainMsg(@RequestParam(required = false) String filter, String filterMsg, Map<String, Object> model) {
        if (isEmpty(filter) && isEmpty(filterMsg)) {
            return getAllSinsAndReturn(model);
        }
        List<Sin> sins;
        if (isEmpty(filter)) {
            sins = sinRepository.findByDescriptionContaining(filterMsg);
        } else if (isEmpty(filterMsg)) {
            sins = sinRepository.findByTypeContaining(filter);
        } else {
            sins = sinRepository.findByDescriptionContainingAndTypeContaining(filterMsg, filter);
        }
        model.put("sins", sins);
        model.put("filter", filter);
        model.put("filterMsg", filterMsg);
        return "main";
    }

    private boolean isEmpty(@RequestParam(required = false) String filter) {
        return filter == null || filter.isEmpty();
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

    @GetMapping("/main/del")
    public String deleteSin(@RequestParam String descr, @RequestParam String type, Map<String, Object> model) {
        Sin sin = sinRepository.findByDescriptionAndType(descr, type).stream().findFirst().orElse(null);
        if (sin != null) {
            sinRepository.delete(sin);
        }
        return getAllSinsAndReturn(model);
    }

    private String getAllSinsAndReturn(Map<String, Object> model) {
        Iterable<Sin> sins = sinRepository.findAll();
        model.put("sins", sins);
        return "main";
    }
}