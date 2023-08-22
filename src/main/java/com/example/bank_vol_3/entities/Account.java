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
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;
    Long userId;
    Integer accountNumber;
    String accountName;
    String accountType;
    BigDecimal balance;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
