package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final RestTemplate rest = new RestTemplate();

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    @GetMapping
    public String Init(Model model) {
        Customer customer = rest.getForObject("http://localhost:9999/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "/frontendhtml/customer_index";
    }

    @GetMapping("/info")
    public String ShowInfo(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "frontendhtml/customer_create_info";
    }

    @GetMapping("/actionRegisterInfo")
    public String ActionRegisterInfo(Model model) {

        return "redirect:/customer/info";
    }

    @GetMapping("/borrow")
    public String ShowBorrow(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "frontendhtml/customer_borrow";
    }

    @GetMapping("/pay")
    public String ShowPay(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "frontendhtml/customer_paymentborrow";
    }

    @GetMapping("/paydetail")
    public String ShowPayDetail(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "frontendhtml/customer_paymentborrow_details";
    }
}
