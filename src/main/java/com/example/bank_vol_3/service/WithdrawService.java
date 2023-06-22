package com.example.bank_vol_3.service;

import com.example.bank_vol_3.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class WithdrawService {
    
    AccountRepository accountRepository;


    public void checkFields(String amount, Long accountId) {
        if (amount.isEmpty() || amount.equals("0")) {
            throw new RuntimeException("Поле вывода не должа быть пусто или равно 0");
        }
        if (amount.charAt(0) == '-') {
            throw new RuntimeException("Сумма вывода не может быть отрицательной");
        }
        if (accountId == null) {
            throw new RuntimeException("Необходимо выбрать аккаунт");
        }
    }

    public void withdraw(double currentBalance, String withdrawAmount, Long accountId) {
        double amount = Double.parseDouble(withdrawAmount);
        if (currentBalance < amount) {
            throw new RuntimeException("На аккаунте не достаточно средств для операции");
        }
        double newBalance = currentBalance - amount;

        accountRepository.changeAccountBalance(newBalance, accountId);
    }
}
