package com.example.myproject.service;


import com.example.myproject.dao.CustomerDao;
import com.example.myproject.model.Customer;
import com.example.myproject.request.CustomerRequest;
import com.example.myproject.response.BaseCustomerResponse;
import com.example.myproject.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CustomerService {
    @Autowired
    CustomerDao dao;

    public BaseCustomerResponse addCustomer(CustomerRequest request) { //
        BaseCustomerResponse base_response = new BaseCustomerResponse();

        try {
            if(request != null) {
                if (request.getPassword().equals(request.getRe_password())) {
                    if (!request.getName().equals("") && !request.getAddress().equals("") && !request.getPhone().equals("")) {
                        if (checkEmailValidity(request.getEmail()) && checkPasswordValidity(request.getPassword())) {
                            if (!exitsByEmail(request.getEmail())) {
                                Customer customer = dao.save(requestToCustomer(request));
                                CustomerResponse response = customerToResponse(customer);
                                String message = "Successfully added profile.";
                                base_response.setResponse(response);
                                base_response.setMessage(message);
                                return base_response;
                            } else {
                                base_response.setMessage("Customer with email: " + request.getEmail() + " already exists!");
                            }
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
            }else{
                base_response.setMessage("Invalid Request");
            }

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e);
        }

        return base_response;
    }

    public BaseCustomerResponse findById(Integer id) {
        BaseCustomerResponse base_response = new BaseCustomerResponse();
        try {
            CustomerResponse response = new CustomerResponse();
            base_response.setMessage("No customer with id: "+ id+" found!");
            if(dao.existsByCustomerId(id)) {
               response = customerToResponse(dao.findCustomerByCustomerId(id));
               base_response.setMessage("Customer with id: "+id+" successfully found.");
            }
            base_response.setResponse(response);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e);
        }
        System.out.println(base_response.getMessage());
        return base_response;
    }

    public List<Customer> getAllCustomer(){
        List<Customer> customerList = new ArrayList<>();
        try{
            customerList = dao.findAll();
        }catch(Exception e){
            System.out.println("Exception Occurred: " + e);
        }
        return customerList;
    }

    public BaseCustomerResponse updateCustomer(Integer id, CustomerRequest request){
        BaseCustomerResponse base_response = new BaseCustomerResponse();
        try{
            if(dao.existsById(id)){
                Customer customer = dao.findCustomerByCustomerId(id);
                if(request != null){
                    if(request.getOld_password().equals(customer.getCustomerPassword())){
                        if(request.getPassword().equals(request.getRe_password()) && checkPasswordValidity(request.getPassword())) {
                            request.setEmail(customer.getCustomerEmail());
                            if ((request.getName().equals(""))) {
                                request.setName(customer.getCustomerName());
                            }
                            if (request.getAddress().equals("")) {
                                request.setAddress(customer.getCustomerAddress());
                            }
                            if (request.getPhone().equals("")) {
                                request.setPhone(customer.getCustomerPhone());
                            }
                            base_response.setResponse(customerToResponse(dao.save(requestToCustomer(request))));
                            base_response.setMessage("Updated Successfully");
                        }
                        else{
                            base_response.setMessage("New Password is not Valid!");
                        }

                    }else{
                        base_response.setMessage("Incorrect Password!");
                    }

                }else{
                    base_response.setMessage("Invalid Request");
                }
            }else{
                base_response.setMessage("Customer with id: "+id+" not found!");
            }
        }
        catch (Exception e){
            System.out.println("Exception Occurred: " + e);
        }
        return base_response;
    }


    private Customer requestToCustomer(CustomerRequest request) { //this method is used to convert Request object to Customer Object
        Customer customer = new Customer();
        customer.setCustomerName(request.getName());
        customer.setCustomerEmail(request.getEmail());
        customer.setCustomerPassword(request.getPassword());
        customer.setCustomerPhone(request.getPhone());
        customer.setCustomerAddress(request.getAddress());
        return customer;
    }

    private CustomerResponse customerToResponse(Customer customer) {// this method is used to convert Customer Object to CustomerResponse object
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getCustomerId());
        response.setName(customer.getCustomerName());
        response.setEmail(customer.getCustomerEmail());
        response.setPhone(customer.getCustomerPhone());
        response.setAddress(customer.getCustomerAddress());
        return response;
    }

    private boolean checkEmailValidity(String email) {
        String pattern = "[a-zA-Z0-9._]*@[a-zA-Z0-9.]*\\.[a-zA-Z]*"; //regular expression for email validation
        return Pattern.matches(pattern, email);
    }

    private boolean checkPasswordValidity(String password) {
        boolean valid = false;
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=!])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password.length() > 7) {

            if (p.matcher(password).matches()) { // regular expression to check password validity
                valid = true;
            }

        }
        return valid;
    }

    private boolean exitsByEmail(String email){
        return dao.existsByCustomerEmail(email);
    }



}
