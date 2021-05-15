package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

    @Value("${backend.endpoint.url}")
    public static String ENDPOINT;

    @Value("${backend.endpoint.user.url}")
    public static String USER_ENDPOINT;

    @Value("${backend.endpoint.staff.url}")
    public static String STAFF_ENDPOINT;

    @Value("${backend.endpoint.customer.url}")
    public static String CUSTOMER_ENDPOINT;
}
