package com.example.demo.models;

import lombok.Data;

@Data
public class Loan {
	private int id;
	private int idCustomer;
	private String purpose;
	private int loanAmount;
	private String createdDate;
	private String dateBegin;
	private String expectedPaymentDate;
	private String collateral;
	private String proofOfCollateralDocument;
	private float income;
	private String proofOfIncomeDocument;
	private int status;
}
