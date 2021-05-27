package com.example.demo.controller;

import com.example.demo.dto.LoanDTO;
import com.example.demo.models.DisbursingModel;
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

    @PostMapping("/add")
    public String addLoan(@Valid LoanDTO loan, BindingResult bindingResult, Model model) {
        String addLoanUrl = BE_ENDPOINT + "/loan";
        if (bindingResult.hasErrors()) {
            return "customer/borrow_page";
        }

        loan.setIdCustomer(Module.Instance.IDCustomer);
        HttpEntity<LoanDTO> entity = new HttpEntity<>(loan);
        rest.exchange(addLoanUrl, HttpMethod.POST, entity, LoanDTO.class);
        return "redirect:/user/home";
    }

    @PostMapping("/disburse/{id}")
    public String disburse(@PathVariable("id") Integer id, @Valid DisbursingModel disbursingModel, Model model) {
        LoanDTO loanDTO = loanService.getLoanById(id);
        loanDTO.setDisbursedAmount(loanDTO.getDisbursedAmount() + disbursingModel.getDisbursingAmount());
        loanService.updateLoan(loanDTO);
        return "redirect:/staff/makeDisbursement/" + id;
    }

    @PutMapping("/update/{id}")
    public String updateLoan(@PathVariable("id") int id, @Valid LoanDTO loan, BindingResult result, Model model) {
        loan.setId(id);
        if (result.hasErrors()) {
            return "#";
        }

        loanService.updateLoan(loan);

        return "redirect:/home/user";
    }

    @GetMapping("/{id}/accept")
    public String acceptLoan(@PathVariable("id") int id, Model model) {
        LoanDTO loanDTO = loanService.getLoanById(id);
        loanDTO.setStatus(2);
        LoanDTO result = loanService.updateLoan(loanDTO);
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);
        model.addAttribute("loan", result);
        return "redirect:/staff/loanManager/" + id;
    }

    @GetMapping("/{id}/deny")
    public String denyLoan(@PathVariable("id") int id, Model model) {
        LoanDTO loanDTO = loanService.getLoanById(id);
        loanDTO.setStatus(0);
        LoanDTO result = loanService.updateLoan(loanDTO);
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);
        model.addAttribute("loan", result);
        return "redirect:/staff/loanManager/" + id;
    }
}
