package com.example.payment_service.repo;

import com.example.payment_service.enitity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}