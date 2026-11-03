package org.example.designpatterns;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CheckoutApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsOrderWithPercentageDiscountAndPixPayment() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Maria Silva",
                                  "items": [
                                    {"productName": "Teclado", "quantity": 2, "unitPrice": 25.00}
                                  ],
                                  "discountCode": "WELCOME10",
                                  "paymentMethod": "PIX"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subtotal").value(50.0))
                .andExpect(jsonPath("$.discount").value(5.0))
                .andExpect(jsonPath("$.total").value(45.0))
                .andExpect(jsonPath("$.paymentMethod").value("PIX"))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void rejectsUnknownDiscountCode() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Maria Silva",
                                  "items": [
                                    {"productName": "Teclado", "quantity": 1, "unitPrice": 25.00}
                                  ],
                                  "discountCode": "INVALIDO",
                                  "paymentMethod": "CARD"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cupom de desconto inválido."));
    }

    @Test
    void rejectsOrderWithoutItems() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Maria Silva",
                                  "items": [],
                                  "paymentMethod": "PIX"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsOrderAboveAggregateQuantityLimit() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Maria Silva",
                                  "items": [
                                    {"productName": "Teclado", "quantity": 101, "unitPrice": 1.00}
                                  ],
                                  "paymentMethod": "PIX"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "A quantidade total do pedido não pode ultrapassar 100 unidades."
                ));
    }

    @Test
    void capsFixedDiscountAtSubtotal() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Maria Silva",
                                  "items": [
                                    {"productName": "Caderno", "quantity": 1, "unitPrice": 3.00}
                                  ],
                                  "discountCode": "SAVE5",
                                  "paymentMethod": "CARD"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subtotal").value(3.0))
                .andExpect(jsonPath("$.discount").value(3.0))
                .andExpect(jsonPath("$.total").value(0.0))
                .andExpect(jsonPath("$.paymentMethod").value("CARD"));
    }
}
