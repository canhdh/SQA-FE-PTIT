package com.example.demo.service;

import com.example.demo.dto.LoanDTO;

import java.util.List;

public interface LoanService {
    LoanDTO getLoanById(int id);

    List<LoanDTO> getALlLoan();
}
