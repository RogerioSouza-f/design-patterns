package org.example.designpatterns.patterns;

import org.example.designpatterns.order.CreateOrderRequest;
import org.example.designpatterns.order.OrderValidationException;

public class CustomerNameValidationHandler extends OrderValidationHandler {
    public CustomerNameValidationHandler(OrderValidationHandler next) {
        super(next);
    }

    @Override
    protected void validateCurrent(CreateOrderRequest request) {
        if (request.customerName() == null || request.customerName().isBlank()) {
            throw new OrderValidationException("O nome do cliente é obrigatório.");
        }
    }
}
