package com.example.myproject.controller;

import com.example.myproject.request.CustomerRequest;
import com.example.myproject.response.BaseCustomerResponse;
import com.example.myproject.response.CustomerResponse;
import com.example.myproject.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {

    @Mock
    CustomerService service;

    @InjectMocks
    CustomerController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private CustomerRequest getRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe@gmail.com", "Password1!", "Password1!", "123 Home Address, city, ST, 12200", "3222331919");

    }

    private CustomerRequest getBadRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe@gmail.com", "Password", "Password", "123 Home Address, city, ST, 12200", "3222331919");

    }

    private CustomerResponse getResponse() {
        CustomerResponse response = new CustomerResponse();
        response.setId(123);
        response.setName("Jhon Doe");
        response.setEmail("jhon.doe@gmail.com");
        response.setPhone("3222331919");
        response.setAddress("123 Home Address, city, ST, 12200");
        return response;
    }

    private CustomerResponse getBadResponse() {
        return new CustomerResponse();
    }

    private ResponseEntity<BaseCustomerResponse> getBaseResponse(CustomerResponse response) {
        BaseCustomerResponse baseCustomerResponse = new BaseCustomerResponse();
        baseCustomerResponse.setResponse(response);
        baseCustomerResponse.setMessage("Successfully added profile.");
        return new ResponseEntity<>(baseCustomerResponse, HttpStatus.CREATED);
    }

    private ResponseEntity<BaseCustomerResponse> getBaseBadResponse(CustomerResponse response) {
        BaseCustomerResponse baseCustomerResponse = new BaseCustomerResponse();
        baseCustomerResponse.setResponse(response);
        baseCustomerResponse.setMessage("Some Error");
        return new ResponseEntity<>(baseCustomerResponse, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void addCustomerTest() {
        ResponseEntity<BaseCustomerResponse> baseResponse = getBaseResponse(getResponse());
        Mockito.when(service.addCustomer(Mockito.any())).thenReturn(baseResponse.getBody());
        ResponseEntity<BaseCustomerResponse> response = controller.addCustomer(getRequest());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getResponse(), baseResponse.getBody().getResponse());
        assertEquals(response.getBody().getMessage(), baseResponse.getBody().getMessage());
        assertEquals(response.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void addCustomerExceptionTest() {
        ResponseEntity<BaseCustomerResponse> baseResponse = getBaseBadResponse(getBadResponse());
        Mockito.when(service.addCustomer(Mockito.any())).thenReturn(baseResponse.getBody());
        ResponseEntity<BaseCustomerResponse> response = controller.addCustomer(getBadRequest());
        assertNull(response.getBody().getResponse().getName());
        assertEquals(baseResponse.getStatusCode(), response.getStatusCode());

    }


}