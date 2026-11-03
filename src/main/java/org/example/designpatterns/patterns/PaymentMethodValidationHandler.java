package org.example.designpatterns.patterns;

import org.example.designpatterns.order.CreateOrderRequest;
import org.example.designpatterns.order.OrderValidationException;
import org.example.designpatterns.order.PaymentMethod;

public class PaymentMethodValidationHandler extends OrderValidationHandler {
    public PaymentMethodValidationHandler(OrderValidationHandler next) {
        super(next);
    }

    @Override
    protected void validateCurrent(CreateOrderRequest request) {
        if (request.paymentMethod() == null) {
            throw new OrderValidationException("A forma de pagamento é obrigatória.");
        }
        if (request.paymentMethod() != PaymentMethod.PIX && request.paymentMethod() != PaymentMethod.CARD) {
            throw new OrderValidationException("Forma de pagamento não suportada.");
        }
    }
}
