package com.example.batchprocessing.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.batchprocessing.model.Customer;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(@SuppressWarnings("null") Customer customer) throws Exception {
        return customer;
    }
}
