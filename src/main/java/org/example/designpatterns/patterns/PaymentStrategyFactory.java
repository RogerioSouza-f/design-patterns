package org.example.designpatterns.patterns;

import java.util.EnumMap;
import java.util.Map;
import org.example.designpatterns.order.OrderValidationException;
import org.example.designpatterns.order.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {
    private final Map<PaymentMethod, PaymentStrategy> strategies = new EnumMap<>(PaymentMethod.class);

    public PaymentStrategyFactory() {
        strategies.put(PaymentMethod.PIX, new PixPaymentStrategy());
        strategies.put(PaymentMethod.CARD, new CardPaymentStrategy());
    }

    public PaymentStrategy create(PaymentMethod method) {
        PaymentStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new OrderValidationException("Forma de pagamento não suportada.");
        }
        return strategy;
    }
}
