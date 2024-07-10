package com.example.batchprocessing.repository;

import com.example.batchprocessing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,Integer> {
}
