package com.ptit.sqa.loan_management_system.model;

import lombok.Data;

@Data
public class Loan {
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
}
