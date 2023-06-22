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
@Table(name = "transact")
public class Transact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transaction_id;
    Long account_id;
    String transaction_type;
    double amount;
    String source;
    String status;
    String reason_code;
    LocalDateTime created_at;
}
