package tn.esprit.microservice.formation.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/payment")

public class PaymentController {

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> data) throws StripeException {
        System.out.println("Received data: " + data); // Log data for debugging
        Long amount = Long.valueOf(data.get("amount").toString()); // in cents
        String name = data.get("name").toString();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/formation")
                .setCancelUrl("http://localhost:4200/formation")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount) // $10.00 -> 1000
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(name)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);
        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());

        // Log the response to verify the ID is being sent correctly
        System.out.println("Checkout session ID: " + session.getId());
        return response;
    }

}
