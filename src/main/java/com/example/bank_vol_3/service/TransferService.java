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
public class TransferService {
    AccountRepository accountRepository;
    TransactService transactService;

    private void checkFields(Long transferFrom, Long transferTo, BigDecimal transferAmount) {
        if (transferFrom == 0) {
            throw new RuntimeException("Укажите аккаунт отправителя");
        }
        if (transferTo == 0) {
            throw new RuntimeException("Укажите аккаунт получателя");
        }
        if (transferAmount.toString().isEmpty() || transferAmount.toString().equals("0") || transferAmount.toString().charAt(0) == '-') {
            throw new RuntimeException("Сумма перевод не может быть ниже 0 или пуста");
        }
        if (transferFrom.equals(transferTo)) {
            throw new RuntimeException("Аккаунт отправителя и получателя должны быть разными");
        }
    }

    public void updateBalance(User user,
                              BigDecimal transferAmount,
                              Long transferFrom,
                              Long transferTo) {
        checkFields(transferFrom, transferTo, transferAmount);

        BigDecimal currentBalanceFrom = transactService.getAccountBalance(user.getId(), transferFrom);
        BigDecimal currentBalanceTo = transactService.getAccountBalance(user.getId(), transferTo);

        BigDecimal minusBalance = currentBalanceFrom.subtract(transferAmount);
        BigDecimal plusBalance = currentBalanceTo.add(transferAmount);

        accountRepository.changeAccountBalance(minusBalance, transferFrom);
        accountRepository.changeAccountBalance(plusBalance, transferTo);
    }
}
