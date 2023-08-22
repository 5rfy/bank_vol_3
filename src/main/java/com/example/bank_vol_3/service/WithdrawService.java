package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class WithdrawService {
    AccountRepository accountRepository;
    TransactService transactService;

    private void checkFields(BigDecimal amount, Long accountId) {
        if (amount.toString().isEmpty() || amount.toString().equals("0")) {
            throw new RuntimeException("Поле вывода не должа быть пусто или равно 0");
        }
        if (amount.toString().charAt(0) == '-') {
            throw new RuntimeException("Сумма вывода не может быть отрицательной");
        }
        if (accountId == null) {
            throw new RuntimeException("Необходимо выбрать аккаунт");
        }
    }

    public void withdraw(User user, BigDecimal withdrawAmount, Long accountId) {
        checkFields(withdrawAmount, accountId);

        BigDecimal currentBalance = transactService.getAccountBalance(user.getId(), accountId);

        if (currentBalance.compareTo(withdrawAmount) < 0) {
            throw new RuntimeException("На аккаунте не достаточно средств для операции");
        }
        BigDecimal newBalance = currentBalance.subtract(withdrawAmount);

        accountRepository.changeAccountBalance(newBalance, accountId);
    }
}
