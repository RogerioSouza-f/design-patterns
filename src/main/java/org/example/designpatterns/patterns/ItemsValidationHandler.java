package org.example.designpatterns.patterns;

import org.example.designpatterns.order.CreateOrderRequest;
import org.example.designpatterns.order.OrderItemRequest;
import org.example.designpatterns.order.OrderValidationException;

public class ItemsValidationHandler extends OrderValidationHandler {
    public ItemsValidationHandler(OrderValidationHandler next) {
        super(next);
    }

    @Override
    protected void validateCurrent(CreateOrderRequest request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new OrderValidationException("O pedido deve conter ao menos um item.");
        }
        if (request.items().size() > 50) {
            throw new OrderValidationException("O pedido não pode conter mais de 50 itens.");
        }
        long totalQuantity = 0;
        for (OrderItemRequest item : request.items()) {
            if (item == null || item.productName() == null || item.productName().isBlank()) {
                throw new OrderValidationException("Todos os itens devem ter um nome de produto.");
            }
            if (item.quantity() < 1) {
                throw new OrderValidationException("A quantidade de cada item deve ser maior que zero.");
            }
            if (item.unitPrice() == null || item.unitPrice().signum() <= 0) {
                throw new OrderValidationException("O preço unitário de cada item deve ser maior que zero.");
            }
            totalQuantity += item.quantity();
            if (totalQuantity > 100) {
                throw new OrderValidationException("A quantidade total do pedido não pode ultrapassar 100 unidades.");
            }
        }
    }
}
