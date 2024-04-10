package org.example.serverapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/health-check")
public class HealthCheckController {

    @RequestMapping
    public String healthCheck() {
        return "OK";
    }
}
