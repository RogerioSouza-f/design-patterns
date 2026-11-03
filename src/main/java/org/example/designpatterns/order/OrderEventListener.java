package org.example.designpatterns.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventListener.class);

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        LOGGER.info("Pedido {} criado para {}", event.order().id(), event.order().customerName());
    }
}
