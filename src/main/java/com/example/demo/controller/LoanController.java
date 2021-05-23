package com.example.demo.controller;

import com.example.demo.models.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/loan")
@CrossOrigin("*")
public class LoanController {

    private final RestTemplate rest = new RestTemplate();

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    @GetMapping("/customerLoans")
    public String showLoanOfCustomer(Model model) {
        String getLoanOfCustomer = BE_ENDPOINT + "/loan/customer/{idCustomer}";
        Map<String, String> params = new HashMap<>();
        params.put("idCustomer", String.valueOf(Module.Instance.IDCustomer));
        ResponseEntity<Loan[]> customerLoans = rest.getForEntity(getLoanOfCustomer, Loan[].class, params);
        List<Loan> loans = Arrays.asList(Objects.requireNonNull(customerLoans.getBody()));
        System.out.println("loans:" + loans);
        model.addAttribute("loans", loans);
        return "customer/payment_page";
    }

    @PostMapping("/add")
    public String addLoan(@Valid Loan loan, BindingResult bindingResult, Model model) {
        String addLoanUrl = BE_ENDPOINT + "/loan";
        if (bindingResult.hasErrors()) {
            return "customer/borrow_page";
        }

        loan.setIdCustomer(Module.Instance.IDCustomer);
        loan.setStatus(1);
        loan.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        HttpEntity<Loan> entity = new HttpEntity<>(loan);
        HttpEntity<Loan> loanHttpEntity = rest.exchange(addLoanUrl, HttpMethod.POST, entity, Loan.class);
        return "redirect:/user/home";
    }

    @PutMapping("/update/{id}")
    public String updateLoan(@PathVariable("id") int id, @Valid Loan loan, BindingResult result, Model model) {
        String updateLoanUrl = BE_ENDPOINT + "/loan";
        loan.setId(id);
        if (result.hasErrors()) {
            return "#";
        }

        HttpEntity<Loan> entity = new HttpEntity<>(loan);
        HttpEntity<Loan> response = rest.exchange(updateLoanUrl, HttpMethod.PUT, entity, Loan.class);
        return "redirect:/home/user";
    }

}
