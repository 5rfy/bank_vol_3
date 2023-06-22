package com.example.bank_vol_3.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long account_id;
    Long user_id;
    Integer accountNumber;
    String account_name;
    String account_type;
    BigDecimal balance;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
