package org.example.designpatterns.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.example.designpatterns.patterns.DiscountStrategy;
import org.example.designpatterns.patterns.DiscountStrategyFactory;
import org.example.designpatterns.patterns.OrderValidationHandler;
import org.example.designpatterns.patterns.PaymentStrategy;
import org.example.designpatterns.patterns.PaymentStrategyFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CheckoutFacade {
    private final OrderValidationHandler validationChain;
    private final DiscountStrategyFactory discountStrategyFactory;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final ApplicationEventPublisher eventPublisher;

    public CheckoutFacade(
            OrderValidationHandler validationChain,
            DiscountStrategyFactory discountStrategyFactory,
            PaymentStrategyFactory paymentStrategyFactory,
            ApplicationEventPublisher eventPublisher
    ) {
        this.validationChain = validationChain;
        this.discountStrategyFactory = discountStrategyFactory;
        this.paymentStrategyFactory = paymentStrategyFactory;
        this.eventPublisher = eventPublisher;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        validationChain.validate(request);

        BigDecimal subtotal = request.items().stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        DiscountStrategy discountStrategy = discountStrategyFactory.create(request.discountCode());
        BigDecimal discount = discountStrategy.discountFor(subtotal)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.subtract(discount).setScale(2, RoundingMode.HALF_UP);

        PaymentStrategy paymentStrategy = paymentStrategyFactory.create(request.paymentMethod());
        OrderResponse order = new OrderResponse(
                UUID.randomUUID(),
                request.customerName().trim(),
                List.copyOf(request.items()),
                subtotal,
                discount,
                total,
                request.discountCode() == null || request.discountCode().isBlank()
                        ? null
                        : request.discountCode().trim().toUpperCase(Locale.ROOT),
                paymentStrategy.method(),
                paymentStrategy.pay(total),
                "CONFIRMED"
        );

        eventPublisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }
}
