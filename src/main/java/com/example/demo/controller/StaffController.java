package com.example.demo.controller;

import com.example.demo.dto.LoanDTO;
import com.example.demo.models.DisbursingModel;
import com.example.demo.models.Staff;
import com.example.demo.service.LoanService;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@RequestMapping("/staff")
@CrossOrigin("*")
public class StaffController {

    private final RestTemplate rest = new RestTemplate();

    private final StaffService staffService;

    private final LoanService loanService;

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    public StaffController(StaffService staffService, LoanService loanService) {
        this.staffService = staffService;
        this.loanService = loanService;
    }

    @GetMapping
    public String showStaffHome(Model model) {
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);
        return "staff/home_page";
    }

    @GetMapping("/loanManager")
    public String loanManager(Model model) {
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);

        List<LoanDTO> loanDTOList = loanService.getAllLoan();
        model.addAttribute("loans", loanDTOList);
        return "admin/loans_profile_manager";
    }

    @GetMapping("/loanManager/{id}")
    public String loanProfileDetails(@PathVariable("id") int id, Model model) {
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);

        LoanDTO loan = loanService.getLoanById(id);

        model.addAttribute("loan", loan);
        return "admin/loans_profile_details_page";
    }

    @GetMapping("/disbursement")
    public String disbursementPage(Model model) {
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("staff", staff);

        List<LoanDTO> loanDTOList = loanService.getAllLoan();
        model.addAttribute("loans", loanDTOList);
        return "admin/disbursement_page";
    }

    @GetMapping("/makeDisbursement/{id}")
    public String showLoanDetail(@PathVariable("id") int id, Model model) {
        LoanDTO loan = loanService.getLoanById(id);
        Staff staff = staffService.getStaffById(Module.Instance.IDStaff);
        model.addAttribute("disbursingModel", new DisbursingModel());
        model.addAttribute("staff", staff);
        model.addAttribute("loan", loan);
        return "admin/confirm_disbursement_page";
    }

    @GetMapping("/loanCheck")
    public String loanCheck(Model model) {
        return "redirect:/user/home";
    }

}
