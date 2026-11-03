package org.example.designpatterns.patterns;

import java.math.BigDecimal;
import org.example.designpatterns.order.PaymentMethod;

public class PixPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentMethod method() {
        return PaymentMethod.PIX;
    }

    @Override
    public String pay(BigDecimal amount) {
        return "Pagamento PIX aprovado (simulado).";
    }
}
