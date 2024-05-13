package org.example.serverapp.controller;

import org.example.serverapp.entity.User;
import org.example.serverapp.repository.ProductRepositoryDB;
import org.example.serverapp.repository.UserRepositoryDB;
import org.example.serverapp.service.GenerateFakeDataService;
import org.example.serverapp.service.ProductService;
import org.example.serverapp.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/health-check")
public class HealthCheckController {

    @RequestMapping
    public String healthCheck() {

        return "OK";

    }
}
