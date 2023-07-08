package com.example.bank_vol_3.repository;

import com.example.bank_vol_3.entities.Transact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactRepository extends JpaRepository<Transact, Long> {
}