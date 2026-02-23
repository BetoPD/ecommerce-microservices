package com.store.AuthServer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.AuthServer.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);
}
