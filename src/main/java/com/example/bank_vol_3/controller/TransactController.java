package com.example.bank_vol_3.controller;

import com.example.bank_vol_3.entities.Account;
import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1")
@Controller
public class TransactController {

    AccountService accountService;
    TransactService transactService;
    TransferService transferService;
    WithdrawService withdrawService;
    PaymentService paymentService;

    @NonFinal
    ModelAndView model;
    @NonFinal
    User user;
    @NonFinal
    List<Account> userAccounts;

    @PostMapping("/deposit")
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("account_id") Long accountId,
                          HttpSession session,
                          RedirectAttributes attributes) {
        try {
            transactService.checkFields(depositAmount, accountId);

            model = new ModelAndView("/components/transact_forms/deposit_form");

            user = (User) session.getAttribute("user");
            userAccounts = accountService.getAccounts(user.getId());

            double currentBalance = transactService.getAccountBalance(user.getId(), accountId);

            transactService.updateBalance(currentBalance, depositAmount, accountId);
            transactService.saveTransact(accountId, "Deposit", Double.parseDouble(depositAmount),
                                    "online", "success", "Deposit Transaction Successful");

            model.addObject("userAccounts", userAccounts);
            attributes.addFlashAttribute("successDeposit", "Депозит успешно отправлен");

            return "redirect:dashboard";
        } catch (RuntimeException e) {
            transactService.saveTransact(accountId, "Deposit", Double.parseDouble(depositAmount),
                    "online", "failed", "Deposit Transaction Successful");
            attributes.addFlashAttribute("depositAmountIsNull", e.getMessage());
            return "redirect:dashboard";
        }
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("transfer_from") Long transferFrom,
                           @RequestParam("transfer_to") Long transferTo,
                           @RequestParam("transfer_amount") String transferAmount,
                           HttpSession session,
                           RedirectAttributes attributes) {
        try {
            transferService.checkFields(transferFrom, transferTo, transferAmount);

            model = new ModelAndView("/components/transact_forms/transfer_form");

            user = (User) session.getAttribute("user");
            userAccounts = accountService.getAccounts(user.getId());

            double currentBalanceFrom = transactService.getAccountBalance(user.getId(), transferFrom);
            double currentBalanceTo = transactService.getAccountBalance(user.getId(), transferTo);

            transferService.updateBalance(currentBalanceFrom, currentBalanceTo, transferAmount, transferFrom, transferTo);
            transactService.saveTransact(transferFrom, "Transfer", Double.parseDouble(transferAmount),
                    "online", "success", "Transfer Transaction Successful");

            model.addObject("userAccounts", userAccounts);
            attributes.addFlashAttribute("successTransfer", "Перевод успешно выполнен");

            return "redirect:dashboard";
        } catch (RuntimeException e) {
            transactService.saveTransact(transferFrom, "Transfer", Double.parseDouble(transferAmount),
                    "online", "failed", "Transfer Transaction Successful");
            attributes.addFlashAttribute("transferError", e.getMessage());
            return "redirect:dashboard";
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("withdrawal_amount") String withdrawAmount,
                           @RequestParam("account_id") Long accountId,
                           HttpSession session,
                           RedirectAttributes attributes) {

        try {
            withdrawService.checkFields(withdrawAmount, accountId);

            model = new ModelAndView("/components/transact_forms/withdraw_form");

            user = (User) session.getAttribute("user");
            userAccounts = accountService.getAccounts(user.getId());

            double currentBalance = transactService.getAccountBalance(user.getId(), accountId);

            withdrawService.withdraw(currentBalance, withdrawAmount, accountId);
            transactService.saveTransact(accountId, "Withdraw", Double.parseDouble(withdrawAmount),
                    "online", "success", "Withdraw Transaction Successful");

            model.addObject("userAccounts", userAccounts);
            attributes.addFlashAttribute("successWithdraw", "Операция проведена успешно");

            return "redirect:dashboard";
        } catch (RuntimeException e) {
            transactService.saveTransact(accountId, "Withdraw", Double.parseDouble(withdrawAmount),
                    "online", "failed", "Withdraw Transaction Successful");
            attributes.addFlashAttribute("withdrawError", e.getMessage());
            return "redirect:dashboard";
        }
    }

    @PostMapping("/payment")
    public String payment(@RequestParam("beneficiary") String beneficiary,
                          @RequestParam("account_number") String accountNumber,
                          @RequestParam("account_id") Long accountId,
                          @RequestParam("reference") String reference,
                          @RequestParam("payment_amount") String paymentAmount,
                          HttpSession session,
                          RedirectAttributes attributes
                          ) {
        try {
            paymentService.checkFields(beneficiary, accountNumber, accountId, paymentAmount);

            model = new ModelAndView("/components/transact_forms/payment_form");
            model = new ModelAndView("payment_history");

            user = (User) session.getAttribute("user");
            userAccounts = accountService.getAccounts(user.getId());

            double currentBalance = transactService.getAccountBalance(user.getId(), accountId);

            paymentService.makePayment(beneficiary, accountNumber, accountId, reference, paymentAmount, currentBalance);
            paymentService.savePayment(beneficiary, accountNumber, paymentAmount, reference);
            transactService.saveTransact(accountId, "Payment", Double.parseDouble(paymentAmount),
                                    "online", "success", "Payment Transaction Successful");

            model.addObject("userAccounts", userAccounts);
            attributes.addFlashAttribute("successPayment", "Перевод успешно выполнен");

            return "redirect:dashboard";
        } catch (RuntimeException e) {
            transactService.saveTransact(accountId, "Payment", Double.parseDouble(paymentAmount),
                    "online", "failed", "Payment Transaction Successful");
            attributes.addFlashAttribute("paymentError", e.getMessage());
            return "redirect:dashboard";
        }
    }
}
