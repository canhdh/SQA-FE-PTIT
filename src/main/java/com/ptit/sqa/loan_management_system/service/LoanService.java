package com.ptit.sqa.loan_management_system.service;

import com.ptit.sqa.loan_management_system.dto.LoanDTO;

import java.util.List;

public interface LoanService {
    LoanDTO getLoanById(int id);

    List<LoanDTO> getAllLoan();

    List<LoanDTO> getLoansByStatus(Integer status);

    LoanDTO updateLoan(LoanDTO loanDTO);
}
