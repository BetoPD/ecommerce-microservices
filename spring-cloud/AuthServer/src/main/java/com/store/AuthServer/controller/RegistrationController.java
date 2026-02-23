package com.store.AuthServer.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.api.auth.RegisterCustomerDTO;
import com.store.AuthServer.model.CustomerEntity;
import com.store.AuthServer.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody RegisterCustomerDTO dto) {
        CustomerEntity customer = authService.registerCustomer(dto);
        return ResponseEntity
                .created(URI.create("/customers/" + customer.getId()))
                .body("Customer registered successfully");
    }
}
