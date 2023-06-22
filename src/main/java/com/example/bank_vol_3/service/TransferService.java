package com.example.bank_vol_3.service;

import com.example.bank_vol_3.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class TransferService {

    AccountRepository accountRepository;

    public void checkFields(Long transferFrom, Long transferTo, String transferAmount) {
        if (transferFrom == 0) {
            throw new RuntimeException("Укажите аккаунт отправителя");
        }
        if (transferTo == 0) {
            throw new RuntimeException("Укажите аккаунт получателя");
        }
        if (transferAmount.isEmpty() || transferAmount.equals("0") || transferAmount.charAt(0) == '-') {
            throw new RuntimeException("Сумма перевод не может быть ниже 0 или пуста");
        }
        if (transferFrom.equals(transferTo)) {
            throw new RuntimeException("Аккаунт отправителя и получателя должны быть разными");
        }
    }

    public void updateBalance(double currentBalanceFrom, double currentBalanceTo, String transferAmount, Long transferFrom, Long transferTo) {
        double amount = Double.parseDouble(transferAmount);
        double minusBalance = currentBalanceFrom - amount;
        double plusBalance = currentBalanceTo + amount;

        accountRepository.changeAccountBalance(minusBalance, transferFrom);
        accountRepository.changeAccountBalance(plusBalance, transferTo);
    }
}
