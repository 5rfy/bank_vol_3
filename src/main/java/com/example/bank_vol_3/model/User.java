package com.example.bank_vol_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "The first name field cannot be empty")
    @Size(min = 3)
    String firstname;
    @NotEmpty(message = "The last name field cannot be empty")
    @Size(min = 3)
    String lastname;
    @Email
    @NotEmpty
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "Почта введена не верно")
    String email;
    @NotEmpty
    @NotNull
    String password;
    UUID token;
    Integer code;
    Integer verified;
    LocalDateTime verified_at;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
