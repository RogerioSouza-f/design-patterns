package org.example.designpatterns.patterns;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal discountFor(BigDecimal subtotal);
}
