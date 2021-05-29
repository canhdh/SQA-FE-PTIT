package com.ptit.sqa.loan_management_system.model;

import lombok.Data;

@Data
public class User {
	private int id;
	private String username;
	private String password;
	private String position;
}
