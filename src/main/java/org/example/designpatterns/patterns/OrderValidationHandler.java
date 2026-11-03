package org.example.designpatterns.patterns;

import org.example.designpatterns.order.CreateOrderRequest;

public abstract class OrderValidationHandler {
    private final OrderValidationHandler next;

    protected OrderValidationHandler(OrderValidationHandler next) {
        this.next = next;
    }

    public final void validate(CreateOrderRequest request) {
        validateCurrent(request);
        if (next != null) {
            next.validate(request);
        }
    }

    protected abstract void validateCurrent(CreateOrderRequest request);
}
