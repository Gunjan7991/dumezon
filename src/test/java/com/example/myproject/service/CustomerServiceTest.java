package com.example.myproject.service;

import com.example.myproject.dao.CustomerDao;
import com.example.myproject.model.Customer;
import com.example.myproject.request.CustomerRequest;
import com.example.myproject.response.BaseCustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerServiceTest {

    @InjectMocks
    CustomerService service;

    @Mock
    CustomerDao dao;

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
        assertNotNull(response);
        assertEquals(response.getResponse().getName(), getCustomer().getCustomerName(), "Checking Customer names");
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
        assertNull(response.getMessage());


    }

}