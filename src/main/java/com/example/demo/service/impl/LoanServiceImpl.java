package com.example.demo.service.impl;

import com.example.demo.dto.LoanDTO;
import com.example.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {

    private final RestOperations restTemplate = new RestTemplate();

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
    public List<LoanDTO> getAllLoan() {
        String getAllLoanUrl = BE_ENDPOINT + "/loan";
        ResponseEntity<LoanDTO[]> loansDTO = restTemplate.getForEntity(getAllLoanUrl, LoanDTO[].class);
        return Arrays.asList(Objects.requireNonNull(loansDTO.getBody()));
    }

    @Override
    public List<LoanDTO> getLoansByStatus(Integer status) {
        String getAllLoanUrl = BE_ENDPOINT + "/loan/status";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getAllLoanUrl)
                .queryParam("status", 2);
        ResponseEntity<LoanDTO[]> loansDTO = restTemplate.getForEntity(builder.toUriString(), LoanDTO[].class);
        return Arrays.asList(Objects.requireNonNull(loansDTO.getBody()));
    }

    @Override
    public LoanDTO updateLoan(LoanDTO loanDTO) {
        String updateLoanUrl = BE_ENDPOINT + "/loan/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(loanDTO.getId()));
        HttpEntity<LoanDTO> entity = new HttpEntity<>(loanDTO);
        LoanDTO result = restTemplate.exchange(updateLoanUrl, HttpMethod.PUT, entity, LoanDTO.class, params).getBody();
        return result;
    }
}
