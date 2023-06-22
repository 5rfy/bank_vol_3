package com.example.bank_vol_3.model;

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
@Table(name = "payment_history")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId;
    Long accountId;
    Long userId;
    String recipientName;
    int recipientAccountNumber;
    double amount;
    String status;
    String reference;
    String reason_code;
    LocalDateTime created_at;
}
