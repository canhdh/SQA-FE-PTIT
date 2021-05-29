package com.ptit.sqa.loan_management_system.service.impl;

import com.ptit.sqa.loan_management_system.model.Customer;
import com.ptit.sqa.loan_management_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final RestOperations restTemplate = new RestTemplate();

    @Value("${backend.endpoint.url}")
    private String BE_ENDPOINT;

    @Override
    public Customer getCustomerById(Integer id) {
        String getCustomerByIdUrl = BE_ENDPOINT + "/customer/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<Customer> customerResponseEntity = restTemplate.exchange(getCustomerByIdUrl,
                HttpMethod.GET, null, Customer.class, params);
        return customerResponseEntity.getBody();
    }
}
