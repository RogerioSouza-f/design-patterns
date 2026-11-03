package org.example.designpatterns.patterns;

import java.math.BigDecimal;

public class FixedDiscountStrategy implements DiscountStrategy {
    private final BigDecimal amount;

    public FixedDiscountStrategy(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public BigDecimal discountFor(BigDecimal subtotal) {
        return amount.min(subtotal);
    }
}
