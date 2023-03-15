package com.example.myproject.dao;

import com.example.myproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerId(Integer id);
    Boolean existsByCustomerEmail(String email);
    Boolean existsByCustomerId(Integer id);

    Customer findByCustomerEmail(String email);
}
