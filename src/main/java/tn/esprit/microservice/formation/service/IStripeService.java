package tn.esprit.microservice.formation.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface IStripeService {
    // Method signature as defined
    Session createCheckoutSession(Long amount, String name) throws StripeException;
}
