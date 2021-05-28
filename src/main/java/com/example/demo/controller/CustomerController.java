package com.example.demo.controller;

import com.example.demo.dto.LoanDTO;
import com.example.demo.models.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        customer.setId(id);
        if (result.hasErrors()) {
            return "customer/edit_customer_page";
        }

        HttpEntity<Customer> entity = new HttpEntity<>(customer);
        HttpEntity<Customer> response = rest.exchange(BE_ENDPOINT + "/customer", HttpMethod.PUT, entity, Customer.class);
        model.addAttribute("");
        return "redirect:/user/home";
    }

    @GetMapping("/borrow")
    public String showCustomerBorrow(Model model, Loan loan) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        model.addAttribute("customer", customer);
        return "customer/borrow_page";
    }

    @GetMapping("/payment")
    public String ShowPay(Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        String getLoanOfCustomer = BE_ENDPOINT + "/loan/customer/{idCustomer}";
        Map<String, String> params = new HashMap<>();
        params.put("idCustomer", String.valueOf(Module.Instance.IDCustomer));
        ResponseEntity<LoanDTO[]> customerLoans = rest.getForEntity(getLoanOfCustomer, LoanDTO[].class, params);
        List<LoanDTO> loans = Arrays.asList(Objects.requireNonNull(customerLoans.getBody()));
        model.addAttribute("loans", loans);
        model.addAttribute("customer", customer);
        return "customer/payment_page";
    }

    @GetMapping("/payment/{paymentId}")
    public String ShowPayDetail(@PathVariable("paymentId") int paymentId, Model model) {
        Customer customer = rest.getForObject(BE_ENDPOINT + "/customer/{id}", Customer.class, Module.Instance.IDCustomer);
        String getLoanOfCustomer = BE_ENDPOINT + "/loan/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(paymentId));
        ResponseEntity<Loan> loan = rest.getForEntity(getLoanOfCustomer, Loan.class, params);
        model.addAttribute("remainTime", this.period(
                        LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        Objects.requireNonNull(loan.getBody()).getExpectedPaymentDate()));
        model.addAttribute("loan", loan.getBody());
        model.addAttribute("customer", customer);
        return "customer/payment_detail_page";
    }

    private String period(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        Period period = Period.between(start, end);
        int years = Math.abs(period.getYears());
        int months = Math.abs(period.getMonths());
        int days = Math.abs(period.getDays());

        return (new StringBuffer())
                .append(years)
                .append(" năm ")
                .append(months)
                .append(" tháng ")
                .append(days)
                .append(" ngày")
                .toString();
    }
}
