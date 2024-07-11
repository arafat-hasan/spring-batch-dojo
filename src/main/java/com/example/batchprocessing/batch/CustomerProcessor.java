package com.example.batchprocessing.batch;

import com.example.batchprocessing.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(@SuppressWarnings("null") Customer customer) throws Exception {
        return customer;
    }
}
