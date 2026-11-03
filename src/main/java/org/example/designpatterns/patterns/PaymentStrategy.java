package org.example.designpatterns.patterns;

import java.math.BigDecimal;
import org.example.designpatterns.order.PaymentMethod;

public interface PaymentStrategy {
    PaymentMethod method();

    String pay(BigDecimal amount);
}
