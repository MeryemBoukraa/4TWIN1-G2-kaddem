package tn.esprit.microservice.formation.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import tn.esprit.microservice.formation.service.StripeService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> data) throws StripeException {
        Long amount = Long.valueOf(data.get("amount").toString());
        String name = data.get("name").toString();

        Session session = stripeService.createCheckoutSession(amount, name);

        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());

        return response;
    }
}
