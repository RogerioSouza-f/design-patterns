package org.example.designpatterns.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateOrderRequest(
        @Size(max = 100) String customerName,
        List<@Valid OrderItemRequest> items,
        @Size(max = 30) String discountCode,
        PaymentMethod paymentMethod
) {
}
