package com.ptit.sqa.loan_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class DefaultController {

    @GetMapping
    public String redirectToHome() {
        return "redirect:/user/login";
    }

    @GetMapping("/error/404")
    public String pageNotFound() {
        return "error/404";
    }

}
