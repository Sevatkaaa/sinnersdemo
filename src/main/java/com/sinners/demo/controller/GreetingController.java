package com.sinners.demo.controller;

import com.sinners.demo.repository.SinRepository;
import com.sinners.demo.repository.UserRepository;
import com.sinners.demo.sin.Sin;
import com.sinners.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@Transactional
public class GreetingController {
    private static final String REDIRECT_MAIN = "redirect:/main";

    @Autowired
    private SinRepository sinRepository;

    @Autowired
    private UserRepository userRepository;

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

    @ExceptionHandler({Exception.class})
    public String databaseError(Exception e) {
        return "redirect:/main";
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
            @RequestParam String sinDescription) {
        Sin sin = new Sin(sinType, sinWeight, sinDescription, user);
        sinRepository.save(sin);
        return REDIRECT_MAIN;
//        return getAllSinsAndReturn(model);
    }

    @GetMapping("/main/del")
    public String deleteSin(@RequestParam String descr, @RequestParam String type) {
        Sin sin = sinRepository.findByDescriptionAndType(descr, type).stream().findFirst().orElse(null);
        if (sin != null) {
            sin.getLikedBy().stream().forEach(user -> {
                user.getLikes().remove(sin);
                userRepository.save(user);
            });
            sinRepository.delete(sin);
        }
        return REDIRECT_MAIN;
    }

    @PostMapping("/main/like")
    public String likeSin(@AuthenticationPrincipal User user, @RequestParam String descr, @RequestParam String type) {
//        Sin sin = sinRepository.findById(id).orElse(null);
        Sin sin = sinRepository.findByDescriptionAndType(descr, type).stream().findFirst  ().orElse(null);
        if (sin != null && !sin.getLikedBy().contains(user)) {
            user.getLikes().add(sin);
            sin.getLikedBy().add(user);
            userRepository.save(user);
            sinRepository.save(sin);
        }
        return REDIRECT_MAIN;
    }

    private String getAllSinsAndReturn(Map<String, Object> model) {
        Iterable<Sin> sins = sinRepository.findAll();
        List<Sin> sortedSins = new ArrayList<>();
        Iterator<Sin> iterator = sins.iterator();
        while (iterator.hasNext()) {
            sortedSins.add(iterator.next());
        }
        sortedSins.sort(((o1, o2) -> o2.getId().compareTo(o1.getId())));
        model.put("sins", sins);
        return "main";
    }
}