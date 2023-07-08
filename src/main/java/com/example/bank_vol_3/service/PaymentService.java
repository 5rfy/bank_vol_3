package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.Account;
import com.example.bank_vol_3.entities.Payment;
import com.example.bank_vol_3.entities.PaymentHistory;
import com.example.bank_vol_3.repository.AccountRepository;
import com.example.bank_vol_3.repository.PaymentHistoryRepository;
import com.example.bank_vol_3.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    AccountRepository accountRepository;
    PaymentRepository paymentRepository;
    TransferService transferService;//TODO
    PaymentHistoryRepository paymentHistoryRepository;

    public void checkFields(String beneficiary,
                            String accountNumber,
                            Long accountId,
                            String paymentAmount) {
        if (accountId == null) {
            throw new RuntimeException("Необходимо выбрать аккаунт");
        }
        if (beneficiary.isEmpty()) {
            throw new RuntimeException("Укажите получателя");
        }
        if (accountNumber.isEmpty()) {
            throw new RuntimeException("Укажите номер счета получателя");
        }
        if (paymentAmount.isEmpty() || paymentAmount.equals("0")) {
            throw new RuntimeException("Сумма перевод не может быть пуста или равна 0");
        }
        if (paymentAmount.charAt(0) == '-') {
            throw new RuntimeException("Сумма перевод не может быть отрицательна");
        }
    }

    public void makePayment(String beneficiary,
                            String accountNumber,
                            Long accountId,
                            String reference,
                            String paymentAmount,
                            double currentBalance) {
        double amount = Double.parseDouble(paymentAmount);
        int recipientNumber = Integer.parseInt(accountNumber);

        Account recipientAccount = checkRecipientAndAmount(amount, currentBalance, recipientNumber);
        double recipientBalance = recipientAccount.getBalance().doubleValue();
        long recipientId = recipientAccount.getAccount_id();


        transferMoney(currentBalance, recipientBalance, amount, accountId, recipientId);

        Payment payment = Payment.builder()
                .account_id(accountId)
                .beneficiary(beneficiary)
                .beneficiary_acc_no(recipientNumber)
                .amount(amount)
                .reference_no(reference)
                .status("success")
                .reason_code("p")
                .created_at(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }

    private void transferMoney(double currentBalance,
                               double recipientBalance,
                               double amount,
                               long accountId,
                               long recipientId) {
        double minusBalance = currentBalance - amount;
        double plusBalance = recipientBalance + amount;

        accountRepository.changeAccountBalance(minusBalance, accountId);
        accountRepository.changeAccountBalance(plusBalance, recipientId);
    }

    private Account checkRecipientAndAmount(double amount, double currentBalance, int recipientNumber) {
        if (amount > currentBalance) {
            throw new RuntimeException("На данном аккаунте не достаточно средств");
        }

        Account recipientAccount = accountRepository.findAccountByAccountNumber(recipientNumber);

        if (recipientAccount != null) {
            return recipientAccount;
        } else {
            throw new RuntimeException("Что то пошло не так");
        }
    }

    public List<PaymentHistory> getAllPayments(Long id) {

        return paymentHistoryRepository.findAllByUserId(id);
    }

    public void savePayment(String beneficiary, String accountNumber, String paymentAmount, String reference) {
        int recipientNumber = Integer.parseInt(accountNumber);
        double recipientBalance = Double.parseDouble(paymentAmount);

        PaymentHistory paymentHistory = PaymentHistory.builder()
                .recipientName(beneficiary)
                .recipientAccountNumber(recipientNumber)
                .amount(recipientBalance)
                .status("success")
                .reference(reference)
                .reason_code("p")
                .created_at(LocalDateTime.now())
                .build();

        paymentHistoryRepository.save(paymentHistory);
    }
}
