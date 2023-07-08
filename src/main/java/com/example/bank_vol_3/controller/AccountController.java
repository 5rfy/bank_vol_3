package com.example.bank_vol_3.controller;

import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Controller
public class AccountController {

    AccountService accountService;

    @PostMapping("/create_account")
    public String createAccount(@RequestParam("account_name") String accountName,
                                @RequestParam("account_type") String accountType,
                                RedirectAttributes attributes,
                                HttpSession session) {

        try {
            accountService.checkNameType(accountName, accountType);
            User user = (User)session.getAttribute("user");
            accountService.createBankAccount(user.getId(), accountName, accountType);
            attributes.addFlashAttribute("success", "Аккаунт успешно создан");
            return "redirect:dashboard";
        } catch(RuntimeException e) {
            attributes.addFlashAttribute("emptyNameType", e.getMessage());
            return "redirect:dashboard";
        }
    }
}
