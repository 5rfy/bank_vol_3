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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId;
    Long accountId;
    String beneficiary;
    int beneficiaryAccNo;
    BigDecimal amount;
    String reference_no;
    String status;
    String reason_code;
    LocalDateTime created_at;

}
