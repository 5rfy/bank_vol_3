package com.example.bank_vol_3.controller;

import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Controller
public class RegisterController {

    UserService userService;

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam String firstname,
                           @RequestParam String lastname,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirm_password,
                           Model model) {
        // checking for password
        if (!password.equals(confirm_password)) {
            model.addAttribute("hasErrorInConfirmPassword", "Пароли не совпадают");
            return "register";
        }
        // checking for valid email
        if (result.hasErrors()) {
            model.addAttribute("hasErrorsInEmail", true);
            return "register";
        }
        // checking for duplicate email
        try {
            userService.registerUser(firstname, lastname, email, password);
            model.addAttribute("successfulRegistration", "Аккаунт успешно зарегистрирован. Проверьте указанную почту для подтверждения аккаунта");
            return "register";
        } catch (RuntimeException exception) {
            model.addAttribute("errorMessageDuplicateEmail", "Пользователь с таким email уже существует");
            return "register";
        }
    }


}
