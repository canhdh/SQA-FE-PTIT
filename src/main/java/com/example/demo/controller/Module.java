package com.example.demo.controller;

public class Module {
	public static Module Instance; 
	public Module() {
		Instance = this;
	}
	public int IDUser;
	public int IDCustomer;
	public int IDStaff;
}
