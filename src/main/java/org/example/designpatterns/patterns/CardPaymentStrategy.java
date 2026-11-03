package org.example.designpatterns.patterns;

import java.math.BigDecimal;
import org.example.designpatterns.order.PaymentMethod;

public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentMethod method() {
        return PaymentMethod.CARD;
    }

    @Override
    public String pay(BigDecimal amount) {
        return "Pagamento no cartão aprovado (simulado).";
    }
}
