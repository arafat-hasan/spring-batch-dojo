package com.example.learnspringbatch.config;

import com.example.learnspringbatch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        return customer;
    }
}
