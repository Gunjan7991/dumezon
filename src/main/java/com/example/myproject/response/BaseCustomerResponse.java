package com.example.myproject.response;

import lombok.Data;

@Data
public class BaseCustomerResponse {
    CustomerResponse response;
    String message;
}
