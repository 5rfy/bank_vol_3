package com.example.bank_vol_3.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long payment_id;
    Long account_id;
    String beneficiary;
    int beneficiary_acc_no;
    double amount;
    String reference_no;
    String status;
    String reason_code;
    LocalDateTime created_at;

}
