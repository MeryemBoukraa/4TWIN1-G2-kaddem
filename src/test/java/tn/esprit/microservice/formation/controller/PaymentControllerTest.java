package tn.esprit.microservice.formation.controller;

import com.stripe.model.checkout.Session;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tn.esprit.microservice.formation.service.StripeService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(PaymentControllerTest.MockConfig.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StripeService stripeService;

    @Test
    void testCreateCheckoutSession() throws Exception {
        Session mockSession = Mockito.mock(Session.class);
        Mockito.when(mockSession.getId()).thenReturn("mock-session-id");

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

    @TestConfiguration
    static class MockConfig {
        @Bean
        public StripeService stripeService() {
            return Mockito.mock(StripeService.class);
        }
    }
}
