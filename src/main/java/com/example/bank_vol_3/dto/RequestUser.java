package com.example.bank_vol_3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {
    private String firstname;
    private String lastname;
    private String email;
    private String hash_pass;
    private String token;
    private String code;

}
