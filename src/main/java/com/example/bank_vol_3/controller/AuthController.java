package com.example.bank_vol_3.controller;

import com.example.bank_vol_3.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Controller
public class AuthController {

    UserService userService;

    @GetMapping("/login")
    public String getLogin(Model model) {
        UUID token = UserService.generateToken();
        model.addAttribute("token", token);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("_token") UUID token,
                        HttpSession session,
                        RedirectAttributes attributes) {

        try{
            userService.authentication(email, password, token, session);
        } catch (RuntimeException e) {

            attributes.addFlashAttribute("invalidEmailOrPassword", e.getMessage());

            return "redirect:login";
        }

        return "redirect:dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,
                         RedirectAttributes attributes) {
        session.invalidate();

        attributes.addFlashAttribute("logged_out", "Logged out successfully");

        return "redirect:login";
    }
}
