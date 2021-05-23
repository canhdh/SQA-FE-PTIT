package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/staff")
@CrossOrigin("*")
public class StaffController {

    private final RestTemplate rest = new RestTemplate();

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

}
