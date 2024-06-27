package org.profitsoft.hw_05_2_gateway_oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class Hw052GatewayOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw052GatewayOauthApplication.class, args);
    }

}
