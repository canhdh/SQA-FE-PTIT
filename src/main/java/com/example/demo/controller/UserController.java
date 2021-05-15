package com.example.demo.controller;

import com.example.demo.models.Customer;
import com.example.demo.models.Staff;
import com.example.demo.models.User;
import com.example.demo.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/user")
public class UserController {

    private User myUser = new User();
    private RestTemplate rest = new RestTemplate();
    public Module module = new Module();

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "/adminhtml/login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (myUser.getPosition().equals("customer")) {
            Customer customer = rest.getForObject(Constants.CUSTOMER_ENDPOINT + "/name/{username}", Customer.class, myUser.getUsername());
            model.addAttribute("customer", customer);
            Module.Instance.IDCustomer = customer.getIDCustomer();
            return "/frontendhtml/customer_index";
        } else {
            Staff staff = rest.getForObject(Constants.STAFF_ENDPOINT + "/name/{username}", Staff.class, myUser.getUsername());
            model.addAttribute("staff", staff);
            Module.Instance.IDStaff = staff.getIDStaff();
            return "/adminhtml/home";
        }
    }

    @PostMapping("/login")
    public String checkLogin(User user, Model model) {
        String username = user.getUsername();
        String password = user.getPassword();

        User userGet = rest.getForObject(Constants.USER_ENDPOINT + "/name/{username}", User.class, username);
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
