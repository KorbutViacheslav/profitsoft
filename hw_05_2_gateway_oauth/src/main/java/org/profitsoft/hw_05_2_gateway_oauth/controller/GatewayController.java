package org.profitsoft.hw_05_2_gateway_oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
