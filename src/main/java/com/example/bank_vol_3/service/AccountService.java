package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.Account;
import com.example.bank_vol_3.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    AccountRepository accountRepository;

    public List<Account> getAccounts(Long id) {
        return accountRepository.findAccountsById(id);
    }

    public BigDecimal getBalance(Long id) {
        return accountRepository.getTotalBalance(id);
    }

    public void checkNameType(String accountName, String accountType) {
        if (accountName.isEmpty()) {
            throw new RuntimeException("Введите имя учетной записи");
        }
        if (accountType.isEmpty()) {
            throw new RuntimeException("Выберите тип учетной записи учетной записи");
        }
    }

    public void createBankAccount(Long id,
                                  String accountName,
                                  String accountType) {
        int accountNumber = generateAccountNumber();

        accountRepository.createBankAccount(id, accountNumber, accountName, accountType);

    }

    private int generateAccountNumber() {
        Random rand = new Random();
        int bound = 1000;

        return bound * rand.nextInt(bound);
    }
}
