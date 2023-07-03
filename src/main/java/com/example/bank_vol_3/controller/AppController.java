package com.example.bank_vol_3.controller;

import com.example.bank_vol_3.model.Account;
import com.example.bank_vol_3.model.PaymentHistory;
import com.example.bank_vol_3.model.User;
import com.example.bank_vol_3.service.AccountService;
import com.example.bank_vol_3.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Controller
public class AppController {

    AccountService accountService;

    PaymentService paymentService;
    ModelAndView name = new ModelAndView("/components/incl/header");

    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session,
                               Model model) {
        ModelAndView balance = new ModelAndView("/components/accounts_display");

        User user = (User) session.getAttribute("user");

        userIsNull(user);

        List<Account> accounts = accountService.getAccounts(user.getId());
        BigDecimal getBalance = accountService.getBalance(user.getId());

        balance.addObject("balance", getBalance);
        name.addObject("user", user);
        model.addAttribute("balance", getBalance);

        model.addAttribute("userAccounts", accounts);
        model.addAttribute("user", user);

        return "dashboard";
    }

    @GetMapping("/payment_history")
    public String getPaymentHistory(HttpSession session,
                                    Model model) {

        User user = (User) session.getAttribute("user");

        name.addObject("user", user);

        List<PaymentHistory> paymentHistory = paymentService.getAllPayments(user.getId());

        model.addAttribute("paymentHistory", paymentHistory);
        model.addAttribute("user", user);

        return "payment_history";
    }

    private void userIsNull(User user) {

        if (user == null) {
            throw new RuntimeException("");
        }
    }
}
