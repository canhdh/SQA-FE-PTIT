package com.example.demo.service.impl;

import com.example.demo.dto.LoanDTO;
import com.example.demo.models.Loan;
import com.example.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${backend.endpoint.url}")
    private String BE_ENDPOINT;

    @Override
    public LoanDTO getLoanById(int id) {
        String getLoanUrl = BE_ENDPOINT + "/loan/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<LoanDTO> loan = restTemplate.getForEntity(getLoanUrl, LoanDTO.class, params);
        return loan.getBody();
    }

    @Override
    public List<LoanDTO> getALlLoan() {
        String getAllLoanUrl = BE_ENDPOINT + "/loan";
        ResponseEntity<LoanDTO[]> loansDTO = restTemplate.getForEntity(getAllLoanUrl, LoanDTO[].class);
        return Arrays.asList(Objects.requireNonNull(loansDTO.getBody()));
    }

}
