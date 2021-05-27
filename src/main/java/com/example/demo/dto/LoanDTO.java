package com.example.demo.dto;

import com.example.demo.models.Customer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanDTO {
    private Integer id;
    private Integer idCustomer;
    private String purpose;
    private Double loanAmount;
    private String createdDate;
    private String dateBegin;
    private String expectedPaymentDate;
    private String collateral;
    private String proofOfCollateralDocument;
    private Double income;
    private String proofOfIncomeDocument;
    private Integer status;
    private Double disbursedAmount;
    private String disbursementDate;
    private Double paidAmount;
    private Customer customer;
}
