package com.example.demo.models;

import lombok.Data;

@Data
public class DebtSlip {
	private int IDDebtSlip;
	private int IDCustomer;
	private String desc;
	private int loan;
	private String date;
	private String dateBegin;
	private String dateEnd;
	private String collateral;
	private String pooDoc;
	private int income;
	private String poIncome;
	private String status;
}
