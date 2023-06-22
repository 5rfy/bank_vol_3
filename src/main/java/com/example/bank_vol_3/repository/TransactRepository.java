package com.example.bank_vol_3.repository;

import com.example.bank_vol_3.model.Transact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactRepository extends JpaRepository<Transact, Long> {
}