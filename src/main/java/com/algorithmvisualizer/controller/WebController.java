package com.algorithmvisualizer.controller;

import com.algorithmvisualizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/sorting")
    public String sorting() {
        return "sorting";
    }
    
    @GetMapping("/guide")
    public String guide() {
        return "algorithm-guide";
    }
    
    @GetMapping("/datastructures")
    public String datastructures() {
        return "datastructures";
    }
    
    @GetMapping("/analytics")
    public String analytics() {
        return "analytics";
    }
    
    @GetMapping("/array")
    public String array() {
        return "array";
    }
    
    @GetMapping("/linkedlist")
    public String linkedlist() {
        return "linkedlist";
    }
    
    @GetMapping("/stack")
    public String stack() {
        return "stack";
    }
    
    @GetMapping("/stack-array")
    public String stackArray() {
        return "stack-array";
    }
    
    @GetMapping("/stack-linkedlist")
    public String stackLinkedList() {
        return "stack-linkedlist";
    }
    
    @GetMapping("/queue")
    public String queue() {
        return "queue";
    }
    
    @GetMapping("/queue-array")
    public String queueArray() {
        return "queue-array";
    }
    
    @GetMapping("/queue-linkedlist")
    public String queueLinkedList() {
        return "queue-linkedlist";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, 
                              @RequestParam String password, @RequestParam String fullName, Model model) {
        try {
            userService.createUser(username, email, password, fullName);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed");
            return "register";
        }
    }
}