package com.example.bank_vol_3.repository;

import com.example.bank_vol_3.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {


    List<PaymentHistory> findAllByUserId(Long userId);
}