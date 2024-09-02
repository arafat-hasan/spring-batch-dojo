package com.example.batchprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batchprocessing.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer,Integer> {
    
}
