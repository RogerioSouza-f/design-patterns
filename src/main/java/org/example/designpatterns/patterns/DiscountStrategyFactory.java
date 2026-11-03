package org.example.designpatterns.patterns;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.example.designpatterns.order.OrderValidationException;
import org.springframework.stereotype.Component;

@Component
public class DiscountStrategyFactory {
    private final Map<String, DiscountStrategy> strategies = Map.of(
            "WELCOME10", new PercentageDiscountStrategy(BigDecimal.TEN),
            "SAVE5", new FixedDiscountStrategy(BigDecimal.valueOf(5))
    );

    public DiscountStrategy create(String code) {
        if (code == null || code.isBlank()) {
            return new NoDiscountStrategy();
        }
        String normalizedCode = code.trim().toUpperCase(Locale.ROOT);
        return Optional.ofNullable(strategies.get(normalizedCode))
                .orElseThrow(() -> new OrderValidationException("Cupom de desconto inválido."));
    }
}
