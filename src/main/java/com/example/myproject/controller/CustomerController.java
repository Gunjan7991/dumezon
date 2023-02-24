package com.example.myproject.controller;

import com.example.myproject.request.CustomerRequest;
import com.example.myproject.response.BaseCustomerResponse;
import com.example.myproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // specialized annotation that incurs @Controller and @ResponseBody annotations
@RequestMapping(value = "/store/v1/api") //used to add default url path
public class CustomerController {
    @Autowired
    CustomerService service;

    @PostMapping("/customer/create")
    public ResponseEntity<BaseCustomerResponse> addCustomer(@RequestBody CustomerRequest request) {
        BaseCustomerResponse response = service.addCustomer(request);
        if (response.getResponse().getName() != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
