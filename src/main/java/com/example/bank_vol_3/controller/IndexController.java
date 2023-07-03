package com.example.bank_vol_3.controller;

import com.example.bank_vol_3.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Controller
public class IndexController {

    UserService userService;

    @GetMapping("/index")
    public String getIndex() {

        return "index";
    }


    @GetMapping("/error")
    public String getError() {

        return "error";
    }
    @GetMapping("/verify")
    public String getVerify(@RequestParam("token") UUID token,
                            @RequestParam("code") Integer code,
                            RedirectAttributes attributes) {
        try {
            userService.verifyAccount(token, code);
            attributes.addFlashAttribute("successVerification", "Аккаунт успешно подтвержден, " +
                    "пожалуйста продолжите на странице авторизации");
            return "redirect:login";
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("errorVerification", "Данная сессия истекла");
            return "redirect:login";
        }
    }
}
