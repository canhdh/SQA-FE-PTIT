package com.ptit.sqa.loan_management_system.controller;

import com.ptit.sqa.loan_management_system.model.Customer;
import com.ptit.sqa.loan_management_system.model.Staff;
import com.ptit.sqa.loan_management_system.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    private User myUser = new User();
    private RestTemplate rest = new RestTemplate();
    public Module module = new Module();

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        Module.Instance.IDCustomer = 0;
        Module.Instance.IDUser = 0;
        Module.Instance.IDStaff = 0;
        return "redirect:/user/login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (myUser.getPosition().equals("customer")) {
            Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/user/{username}", Customer.class, myUser.getUsername());
            model.addAttribute("customer", customer);
            assert customer != null;
            Module.Instance.IDCustomer = customer.getId();
            return "customer/home_page";
        }
        if (myUser.getPosition().equals("staff")) {
            Staff staff = rest.getForObject(BE_ENDPOINT + "/staff/user/{username}", Staff.class, myUser.getUsername());
            model.addAttribute("staff", staff);
            assert staff != null;
            Module.Instance.IDStaff = staff.getId();
            return "admin/home_page";
        }
        return "error/404";
    }

    @PostMapping("/login")
    public String checkLogin(User user, Model model) {
        String username = user.getUsername();
        String password = user.getPassword();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("username", username);

        User userGet = rest.getForObject(BE_ENDPOINT + "/user/name/{username}", User.class, uriVariables);
        if (userGet == null) return "redirect:/user/login";
        if (userGet.getPassword().equals(password)) {
            myUser = userGet;
            Module.Instance.IDUser = userGet.getId();
            return "redirect:/user/home";
        } else {
            return "redirect:/user/login";
        }
    }
}
