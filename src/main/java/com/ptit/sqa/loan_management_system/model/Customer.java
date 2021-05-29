package com.ptit.sqa.loan_management_system.model;

import lombok.Data;

@Data
public class Customer {
	private int id;
	private String username;
	private String name;
	private String email;
	private String birthday;
	private String address;
	private String gender;
	private String phoneNumber;
	private String idCard;
	private String nationality;
	private String maritalStatus;
}