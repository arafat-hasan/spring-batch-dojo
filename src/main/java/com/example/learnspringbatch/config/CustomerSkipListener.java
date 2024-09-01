package com.example.learnspringbatch.config;

import com.example.learnspringbatch.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class CustomerSkipListener implements SkipListener<Customer, Customer> {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSkipListener.class);

    @Override
    public void onSkipInRead(Throwable t) {
        logger.warn("Skipped during read due to: {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(Customer item, Throwable t) {
        logger.warn("Skipped item during writing. Item: {}. Reason: {}", item, t.getMessage());
    }

    @Override
    public void onSkipInProcess(Customer item, Throwable t) {
        logger.warn("Skipped item during processing. Item: {}. Reason: {}", item, t.getMessage());
    }
}
