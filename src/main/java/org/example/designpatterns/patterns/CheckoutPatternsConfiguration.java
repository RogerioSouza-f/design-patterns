package org.example.designpatterns.patterns;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckoutPatternsConfiguration {
    @Bean
    public OrderValidationHandler orderValidationChain() {
        return new CustomerNameValidationHandler(
                new ItemsValidationHandler(new PaymentMethodValidationHandler(null))
        );
    }
}
