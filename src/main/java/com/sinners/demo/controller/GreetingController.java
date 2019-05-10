package com.sinners.demo.controller;

import com.sinners.demo.repository.SinRepository;
import com.sinners.demo.sin.Sin;
import com.sinners.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    public static final String REDIRECT_MAIN = "redirect:/main";
    @Autowired
    private SinRepository sinRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        if (model.get("sins") != null) {
            return "main";
        }
        return getAllSinsAndReturn(model);
    }

    @GetMapping("/main/search")
    public String mainMsg(@RequestParam(required = false) String filter, String filterMsg, RedirectAttributes redirectAttributes) {
        if (isEmpty(filter) && isEmpty(filterMsg)) {
            return REDIRECT_MAIN;
        }
        List<Sin> sins;
        if (isEmpty(filter)) {
            sins = sinRepository.findByDescriptionContaining(filterMsg);
        } else if (isEmpty(filterMsg)) {
            sins = sinRepository.findByTypeContaining(filter);
        } else {
            sins = sinRepository.findByDescriptionContainingAndTypeContaining(filterMsg, filter);
        }
        redirectAttributes.addFlashAttribute("sins", sins);
        redirectAttributes.addFlashAttribute("filter", filter);
        redirectAttributes.addFlashAttribute("filterMsg", filterMsg);
        return REDIRECT_MAIN;
    }

    private boolean isEmpty(String filter) {
        return filter == null || filter.isEmpty();
    }

    @PostMapping("/main/add")
    public String addSin(
            @AuthenticationPrincipal User user,
            @RequestParam String sinType,
            @RequestParam Integer sinWeight,
            @RequestParam String sinDescription,
            Map<String, Object> model) {
        Sin sin = new Sin(sinType, sinWeight, sinDescription, user);
        sinRepository.save(sin);
        return REDIRECT_MAIN;
//        return getAllSinsAndReturn(model);
    }

    @GetMapping("/main/del")
    public String deleteSin(@RequestParam String descr, @RequestParam String type) {
        Sin sin = sinRepository.findByDescriptionAndType(descr, type).stream().findFirst().orElse(null);
        if (sin != null) {
            sinRepository.delete(sin);
        }
        return REDIRECT_MAIN;
    }

    private String getAllSinsAndReturn(Map<String, Object> model) {
        Iterable<Sin> sins = sinRepository.findAll();
        model.put("sins", sins);
        return "main";
    }
}