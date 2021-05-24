package com.example.demo.service.impl;

import com.example.demo.controller.Module;
import com.example.demo.models.Staff;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffServiceImpl implements StaffService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${backend.endpoint.url}")
    public String BE_ENDPOINT;

    @Override
    public Staff getStaffById(int id) {
        String getStaffUrl = BE_ENDPOINT + "/staff/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(Module.Instance.IDStaff));
        return restTemplate.getForObject(getStaffUrl, Staff.class, params);
    }
}
