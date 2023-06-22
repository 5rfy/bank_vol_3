package com.example.bank_vol_3.repository;

import com.example.bank_vol_3.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}