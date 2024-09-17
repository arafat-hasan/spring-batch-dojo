package com.example.learnspringbatch.repository;

import com.example.learnspringbatch.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
