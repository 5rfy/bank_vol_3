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
@Table(name = "transact")
public class Transact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transaction_id;
    Long account_id;
    String transaction_type;
    BigDecimal amount;
    String source;
    String status;
    String reason_code;
    LocalDateTime created_at;
}
