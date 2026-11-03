package org.example.designpatterns.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerName,
        List<OrderItemRequest> items,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal total,
        String discountCode,
        PaymentMethod paymentMethod,
        String paymentMessage,
        String status
) {
}
