package com.example.demo.controller;

import com.example.demo.dto.LoanDTO;
import com.example.demo.models.Staff;
import com.example.demo.service.LoanService;
import com.example.demo.service.StaffService;
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

    private final LoanService loanService;
    private final StaffService staffService;

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    public LoanController(LoanService loanService, StaffService staffService) {
        this.loanService = loanService;
        this.staffService = staffService;
    }

    @GetMapping("/customerLoans")
    public String showLoanOfCustomer(Model model) {
        String getLoanOfCustomer = BE_ENDPOINT + "/loan/customer/{idCustomer}";
        Map<String, String> params = new HashMap<>();
        params.put("idCustomer", String.valueOf(Module.Instance.IDCustomer));
        ResponseEntity<LoanDTO[]> customerLoans = rest.getForEntity(getLoanOfCustomer, LoanDTO[].class, params);
        List<LoanDTO> loans = Arrays.asList(Objects.requireNonNull(customerLoans.getBody()));
        System.out.println("loans:" + loans);
        model.addAttribute("loans", loans);
        return "customer/payment_page";
    }

    @GetMapping("/{id}")
    public String showLoanDetail(@PathVariable("id") int id, Model model) {
        LoanDTO loan = loanService.getLoanById(id);
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);
        model.addAttribute("loan", loan);
        return "admin/confirm_disbursement_page";
    }

    @PostMapping("/add")
    public String addLoan(@Valid LoanDTO loan, BindingResult bindingResult, Model model) {
        String addLoanUrl = BE_ENDPOINT + "/loan";
        if (bindingResult.hasErrors()) {
            return "customer/borrow_page";
        }

        loan.setIdCustomer(Module.Instance.IDCustomer);
        loan.setStatus(1);
        loan.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        HttpEntity<LoanDTO> entity = new HttpEntity<>(loan);
        rest.exchange(addLoanUrl, HttpMethod.POST, entity, LoanDTO.class);
        return "redirect:/user/home";
    }

    @PutMapping("/update/{id}")
    public String updateLoan(@PathVariable("id") int id, @Valid LoanDTO loan, BindingResult result, Model model) {
        String updateLoanUrl = BE_ENDPOINT + "/loan";
        loan.setId(id);
        if (result.hasErrors()) {
            return "#";
        }

        HttpEntity<LoanDTO> entity = new HttpEntity<>(loan);
        rest.exchange(updateLoanUrl, HttpMethod.PUT, entity, LoanDTO.class);
        return "redirect:/home/user";
    }

}
