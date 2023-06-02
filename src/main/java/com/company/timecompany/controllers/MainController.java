package com.company.timecompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    private String viewHomePage() {
        return "index";
    }

    @GetMapping("/login")
    private String viewLoginPage() {
        return "/login";
    }
}
