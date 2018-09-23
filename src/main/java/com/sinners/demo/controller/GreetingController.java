package com.sinners.demo.controller;

import com.sinners.demo.repository.SinRepository;
import com.sinners.demo.sin.Sin;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Sinner") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        return getAllSinsAndReturn(model);
    }

    @PostMapping
    public String addSin(@RequestParam String sinType, @RequestParam Integer sinWeight, @RequestParam String sinDescription,  Map<String, Object> model) {
        Sin sin = new Sin(sinType, sinWeight, sinDescription);
        sinRepository.save(sin);
        return getAllSinsAndReturn(model);
    }

    private String getAllSinsAndReturn(Map<String, Object> model) {
        Iterable<Sin> sins = sinRepository.findAll();
        model.put("sins", sins);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        if (filter == null || filter.isEmpty()) {
            return getAllSinsAndReturn(model);
        }
        List<Sin> sins = sinRepository.findByType(filter);
        model.put("sins", sins);
        return "main";
    }
}