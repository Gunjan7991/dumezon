package com.example.myproject.controller;

import com.example.myproject.model.Customer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CustomerControllerTest {

    @Mock
    CustomerService service;

    @Autowired
    CustomerController cc;

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

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("Jhon Doe");
        customer.setCustomerEmail("jhon.doe@gmail.com");
        customer.setCustomerPhone("3222331919");
        customer.setCustomerAddress("123 Home Address, city, ST, 12200");
        customer.setCustomerPassword("Password1!");
        return customer;
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


    @Test
    void getCustomerTest() {
        ResponseEntity<BaseCustomerResponse> baseResponse = getBaseResponse(getResponse());
        Mockito.when((service.findById(Mockito.any()))).thenReturn(baseResponse.getBody());
        ResponseEntity<BaseCustomerResponse> response = controller.getCustomer(123);
        assertNotNull(response.getBody().getResponse());
        assertEquals(baseResponse.getBody().getResponse().getId(), response.getBody().getResponse().getId());
    }

    @Test
    void getCustomerExceptionTest(){
        ResponseEntity<BaseCustomerResponse> baseResponse = getBaseResponse(getBadResponse());
        Mockito.when(service.findById(0)).thenReturn(baseResponse.getBody());
        ResponseEntity<BaseCustomerResponse> response = controller.getCustomer(0);
        assertNull(response.getBody().getResponse().getId());
        assertEquals(baseResponse.getBody().getResponse().getId(), response.getBody().getResponse().getId());
    }

    @Test
    void getAllCustomerTest() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(getCustomer());
        Mockito.when(service.getAllCustomer()).thenReturn(customerList);
        cc.addCustomer(getRequest());
        ResponseEntity<List<Customer>> cList = cc.getAllCustomer();
        assertNotNull(cList);
        assertEquals(customerList.get(0).getCustomerId(), cList.getBody().get(0).getCustomerId());
    }

    @Test
    void getAllCustomerExceptionTest() {
        List<Customer> customerList = new ArrayList<>();
        Mockito.when(service.getAllCustomer()).thenReturn(customerList);
        ResponseEntity<List<Customer>> cList = cc.getAllCustomer();
        assertNotNull(cList);
        assertTrue(cList.getBody().isEmpty());
    }
}