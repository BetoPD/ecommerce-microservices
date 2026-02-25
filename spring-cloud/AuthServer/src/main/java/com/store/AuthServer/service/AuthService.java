package com.store.AuthServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.app.api.auth.RegisterCustomerDTO;
import com.store.app.api.exceptions.InvalidInputException;
import com.store.AuthServer.model.Authority;
import com.store.AuthServer.model.Customer;
import com.store.AuthServer.model.CustomerEntity;
import com.store.AuthServer.repository.CustomerRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity customerEntity = customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + username));
        return new Customer(customerEntity);
    }

    @Transactional
    public CustomerEntity registerCustomer(RegisterCustomerDTO dto) {
        if (customerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new InvalidInputException("Email already registered: " + dto.getEmail());
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());
        customer.setZip(dto.getZip());
        customer.setCountry(dto.getCountry());

        Authority userRole = new Authority();
        userRole.setName("ROLE_USER");
        userRole.setCustomer(customer);
        customer.setAuthorities(List.of(userRole));

        return customerRepository.save(customer);
    }
}
