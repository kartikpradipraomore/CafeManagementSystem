package com.cafemanagementsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafemanagementsys.entity.Admin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String getMethodName(Model model) {
        model.addAttribute("admin", new Admin());
        return "/admin/login";
    }

    @PostMapping
    public String checkAdmin(@RequestParam String email, @RequestParam String password) {
        System.out.println("Post method accessed");
        System.out.println("Email============================================================================================================================================================================================================================================================================================================================: " + email);
        System.out.println("Password: " + password);
        return "Done";
    }

}
