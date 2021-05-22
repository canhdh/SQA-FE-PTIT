package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.Customer;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final RestTemplate rest = new RestTemplate();

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    @GetMapping
    public String showCustomerHome(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "customer/home_page";
    }

    @GetMapping("/edit/{id}")
    public String shoshowEditCustomerInfowUpdateForm(@PathVariable("id") int id, Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);

        if (customer != null) {
            List<String> genders = new ArrayList<String>();
            List<String> nationalities = new ArrayList<>();
            genders.add("Nam");
            genders.add("Nữ");
            nationalities.add("Việt Nam");
            nationalities.add("USA");
            nationalities.add("France");
            nationalities.add("England");
            model.addAttribute("genders", genders);
            model.addAttribute("nationalities", nationalities);
            model.addAttribute("customer", customer);

            return "customer/edit_customer_page";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") int id, @Valid Customer customer, BindingResult result, Model model) {
        customer.setIDCustomer(id);
        if (result.hasErrors()) {
            return "customer/edit_customer_page";
        }

        Map<String, String> params = new HashMap<String, String >();
        params.put("id", String.valueOf(id));
        HttpEntity<Customer> entity = new HttpEntity<>(customer);
        HttpEntity<Customer> response = rest.exchange(BE_ENDPOINT + "/customer/{id}", HttpMethod.PUT, entity, Customer.class, params);
        model.addAttribute("");
        return "redirect:/user/home";
    }

    @GetMapping("/borrow")
    public String showCustomerBorrow(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "customer/borrow_page";
    }

    @GetMapping("/payment")
    public String ShowPay(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "customer/payment_page";
    }

    @GetMapping("/paymentDetail")
    public String ShowPayDetail(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "customer/payment_detail_page";
    }
}
