package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.Account;
import com.example.bank_vol_3.entities.Payment;
import com.example.bank_vol_3.entities.PaymentHistory;
import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.repository.AccountRepository;
import com.example.bank_vol_3.repository.PaymentHistoryRepository;
import com.example.bank_vol_3.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    AccountRepository accountRepository;
    PaymentRepository paymentRepository;
    TransactService transactService;
    TransferService transferService;//TODO
    PaymentHistoryRepository paymentHistoryRepository;

    private void checkFields(String beneficiary,
                             String accountNumber,
                             Long accountId,
                             BigDecimal paymentAmount) {
        if (accountId == null) {
            throw new RuntimeException("Необходимо выбрать аккаунт");
        }
        if (beneficiary.isEmpty()) {
            throw new RuntimeException("Укажите получателя");
        }
        if (accountNumber.isEmpty()) {
            throw new RuntimeException("Укажите номер счета получателя");
        }
        if (paymentAmount.toString().isEmpty() || paymentAmount.toString().equals("0")) {
            throw new RuntimeException("Сумма перевод не может быть пуста или равна 0");
        }
        if (paymentAmount.toString().charAt(0) == '-') {
            throw new RuntimeException("Сумма перевод не может быть отрицательна");
        }
    }

    public void makePayment(User user,
                            String beneficiary,
                            String accountNumber,
                            Long accountId,
                            String reference,
                            BigDecimal paymentAmount) {
        checkFields(beneficiary, accountNumber, accountId, paymentAmount);
        int recipientNumber = Integer.parseInt(accountNumber);
        BigDecimal currentBalance = transactService.getAccountBalance(user.getId(), accountId);
        Account recipientAccount = checkRecipientAndAmount(paymentAmount, currentBalance, recipientNumber);
        BigDecimal recipientBalance = recipientAccount.getBalance();
        long recipientId = recipientAccount.getAccountId();


        transferMoney(currentBalance, recipientBalance, paymentAmount, accountId, recipientId);

        Payment payment = Payment.builder()
                .accountId(accountId)
                .beneficiary(beneficiary)
                .beneficiaryAccNo(recipientNumber)
                .amount(paymentAmount)
                .reference_no(reference)
                .status("success")
                .reason_code("p")
                .created_at(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        savePayment(beneficiary, accountNumber, paymentAmount, reference);
    }

    private void transferMoney(BigDecimal currentBalance,
                               BigDecimal recipientBalance,
                               BigDecimal amount,
                               long accountId,
                               long recipientId) {
        BigDecimal minusBalance = currentBalance.subtract(amount);
        BigDecimal plusBalance = recipientBalance.add(amount);

        accountRepository.changeAccountBalance(minusBalance, accountId);
        accountRepository.changeAccountBalance(plusBalance, recipientId);
    }

    private Account checkRecipientAndAmount(BigDecimal amount, BigDecimal currentBalance, int recipientNumber) {
        if (amount.doubleValue() > currentBalance.doubleValue()) {
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

    public void savePayment(String beneficiary, String accountNumber, BigDecimal paymentAmount, String reference) {
        int recipientNumber = Integer.parseInt(accountNumber);

        PaymentHistory paymentHistory = PaymentHistory.builder()
                .recipientName(beneficiary)
                .recipientAccountNumber(recipientNumber)
                .amount(paymentAmount)
                .status("success")
                .reference(reference)
                .reason_code("p")
                .created_at(LocalDateTime.now())
                .build();

        paymentHistoryRepository.save(paymentHistory);
    }
}
