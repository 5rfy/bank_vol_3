package com.example.bank_vol_3.controller_advisor;

import com.example.bank_vol_3.entities.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerAdvisor {

    @ModelAttribute("registerUser")
    public User getUserDefaults() {
        return new User();
    }
}
