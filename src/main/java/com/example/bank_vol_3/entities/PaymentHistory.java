package com.example.bank_vol_3.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
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
    BigDecimal amount;
    String status;
    String reference;
    String reason_code;
    LocalDateTime created_at;
}
