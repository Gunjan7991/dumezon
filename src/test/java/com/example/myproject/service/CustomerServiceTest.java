package com.example.myproject.service;

import com.example.myproject.dao.CustomerDao;
import com.example.myproject.model.Customer;
import com.example.myproject.request.CustomerRequest;
import com.example.myproject.response.BaseCustomerResponse;
import com.sun.source.tree.AssertTree;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
@Transactional
@SpringBootTest
class CustomerServiceTest {

    @InjectMocks
    CustomerService service;

    @Autowired
    CustomerService cs;

    @Mock
    CustomerDao dao;

    @Before
    public void run(){
        cs.addCustomer(getRequest());
    }

    @BeforeEach
    public void setup() {
        try {
            MockitoAnnotations.openMocks(this);
        }catch(Exception e){
            System.out.println("Exception occurred when Mocking");
        }
    }




    /*public CustomerResponse getResponse() {
        CustomerResponse response = new CustomerResponse();
        response.setId(123);
        response.setName("Jhon Doe");
        response.setEmail("jhon.doe@gmail.com");
        response.setPhone("3222331919");
        response.setAddress("123 Home Address, city, ST, 12200");
        return response;
    }*/

    public Customer getCustomer() {
        return new Customer(1, "Jhon Doe", "123 Home Address, city, ST, 12200", "jhon.doe@gmail.com", "Password1!", "3222331919");
    }
    public Customer getBadCustomer() {
        return new Customer();
    }

    public CustomerRequest getRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe@gmail.com", "Password1!", "Password1!", "123 Home Address, city, ST, 12200", "3222331919");

    }

    public CustomerRequest getWrongPasswordRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe@gmail.com", "Password1!", "Password1!!", "123 Home Address, city, ST, 12200", "3222331919");

    }

    public CustomerRequest getEmptyFieldRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe@gmail.com", "Password1!", "Password1!", "", "3222331919");

    }

    public CustomerRequest getInvalidPasswordRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe@gmail.com", "Pass", "Pass", "123 Home Address, city, ST, 12200", "3222331919");

    }

    public CustomerRequest getInvalidEmailRequest() {
        return new CustomerRequest("Jhon Doe", "jhon.doe", "Password1!", "Password1!", "123 Home Address, city, ST, 12200", "3222331919");

    }

    @Test
    void addCustomerSuccessTest() {
        Mockito.when(dao.save(Mockito.any())).thenReturn(getCustomer());
        BaseCustomerResponse response = service.addCustomer(getRequest());
        System.out.println(response);
        assertNotNull(response);
        assertEquals(getCustomer().getCustomerName(), response.getResponse().getName());
    }

    @Test
    void addCustomerFailTest() {
        Mockito.when(dao.save(Mockito.any())).thenReturn(getCustomer());
        //wrong password
        BaseCustomerResponse response = service.addCustomer(getWrongPasswordRequest());
        assertNull(response.getResponse());
        assertEquals("Password did not match.", response.getMessage());

        //Invalid Email
        response = service.addCustomer(getInvalidEmailRequest());
        assertNull(response.getResponse());
        assertEquals("Invalid Email.", response.getMessage());

        //Password Invalid.
        response = service.addCustomer(getInvalidPasswordRequest());
        assertNull(response.getResponse());
        assertEquals("Password Invalid.", response.getMessage());

        //Empty Field
        response = service.addCustomer(getEmptyFieldRequest());
        assertNull(response.getResponse());
        assertEquals("Empty field", response.getMessage());

        //Exception
        response = service.addCustomer(null);
        assertNull(response.getResponse());
        assertNotNull(response.getMessage());


    }

    @Transactional
    @Test
    void findByIDTest(){
        run();
        Mockito.when(dao.findCustomerByCustomerId(Mockito.any())).thenReturn(getCustomer());
        BaseCustomerResponse response = cs.findById(2);
        System.out.println(cs.getAllCustomer());
        assertNotNull(response);
        assertEquals(getCustomer().getCustomerName(), response.getResponse().getName());
    }

    @Transactional
    @Test
    void findByIDExceptionTest(){
        Mockito.when(dao.findCustomerByCustomerId(Mockito.any())).thenReturn(getBadCustomer());
        BaseCustomerResponse response = cs.findById(2);
        assertNull(response.getResponse().getId());
        assertEquals(getBadCustomer().getCustomerName(),response.getResponse().getName());
    }

    @Transactional
    @Test
    void getAllCustomerTest() {
        run();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(getCustomer());
        Mockito.when(dao.findAll()).thenReturn(customerList);
        List<Customer> cList = cs.getAllCustomer();
        System.out.println(cList);
        assertFalse(cList.isEmpty());
        assertEquals(customerList.get(0).getCustomerId(), cList.get(0).getCustomerId());
    }

    @Test
    void updateCustomerTest() {

    }

    @Test
    void updateCustomerExceptionTest() {
    }

    @Transactional
    @Test
    void getAllCustomerExceptionTest() {
        List<Customer> customerList = new ArrayList<>();
        Mockito.when(dao.findAll()).thenReturn(customerList);
        List<Customer> cList = cs.getAllCustomer();
        assertTrue(cList.isEmpty());
        assertNotNull(cList);
    }
}