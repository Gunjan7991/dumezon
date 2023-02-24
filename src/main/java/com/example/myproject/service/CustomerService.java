package com.example.myproject.service;


import com.example.myproject.dao.CustomerDao;
import com.example.myproject.model.Customer;
import com.example.myproject.request.CustomerRequest;
import com.example.myproject.response.BaseCustomerResponse;
import com.example.myproject.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class CustomerService {
    @Autowired
    CustomerDao dao;

    public BaseCustomerResponse addCustomer(CustomerRequest request) { //
        BaseCustomerResponse base_response = new BaseCustomerResponse();

        try {
            if (request.getPassword().equals(request.getRe_password())) {
                if (!request.getName().equals("") && !request.getAddress().equals("") && !request.getPhone().equals("")) {
                    if (checkEmailValidity(request.getEmail()) && checkPasswordValidity(request.getPassword())) {
                        Customer customer = dao.save(requestToCustomer(request));
                        CustomerResponse response = customerToResponse(customer);
                        String message = "Successfully added profile.";
                        base_response.setResponse(response);
                        base_response.setMessage(message);
                        return base_response;
                    } else {
                        if (!checkEmailValidity(request.getEmail())) {
                            base_response.setMessage("Invalid Email.");
                        } else {
                            base_response.setMessage("Password Invalid.");
                        }
                    }

                } else {
                    base_response.setMessage("Empty field");
                }
                return base_response;
            } else {
                base_response.setMessage("Password did not match.");
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e);
        }

        return base_response;
    }

    protected Customer requestToCustomer(CustomerRequest request) { //this method is used to convert Request object to Customer Object
        Customer customer = new Customer();
        customer.setCustomerName(request.getName());
        customer.setCustomerEmail(request.getEmail());
        customer.setCustomerPassword(request.getPassword());
        customer.setCustomerPhone(request.getPhone());
        customer.setCustomerAddress(request.getAddress());
        return customer;
    }

    protected CustomerResponse customerToResponse(Customer customer) {// this method is used to convert Customer Object to CustomerResponse object
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getCustomerId());
        response.setName(customer.getCustomerName());
        response.setEmail(customer.getCustomerEmail());
        response.setPhone(customer.getCustomerPhone());
        response.setAddress(customer.getCustomerAddress());
        return response;
    }

    protected boolean checkEmailValidity(String email) {
        String pattern = "[a-zA-Z0-9._]*@[a-zA-Z0-9.]*\\.[a-zA-Z]*"; //regular expression for email validation
        return Pattern.matches(pattern, email);
    }

    protected boolean checkPasswordValidity(String password) {
        boolean valid = false;
        if (password.length() > 7) {
            if (Pattern.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$", password)) { // regular expression to check password validity
                valid = true;
            }
        }
        return valid;
    }

}
