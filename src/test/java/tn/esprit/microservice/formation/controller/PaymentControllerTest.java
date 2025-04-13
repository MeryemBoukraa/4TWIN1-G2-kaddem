package tn.esprit.microservice.formation.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.mockito.Mock;
import tn.esprit.microservice.formation.service.StripeService;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StripeService stripeService; // 👈 this is important!

    @Test
    void testCreateCheckoutSession() throws Exception {
        // Mock Stripe Session
        Session mockSession = Mockito.mock(Session.class);
        Mockito.when(mockSession.getId()).thenReturn("mock-session-id");

        // Mock the service call
        Mockito.when(stripeService.createCheckoutSession(2000L, "Test Product"))
                .thenReturn(mockSession);

        String requestBody = """
            {
              "amount": 2000,
              "name": "Test Product"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment/create-checkout-session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("mock-session-id"));
    }
}
