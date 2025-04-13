package tn.esprit.microservice.kassil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class KassilApplication {

    public static void main(String[] args) {
        SpringApplication.run(KassilApplication.class, args);
    }

}
