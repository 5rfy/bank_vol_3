package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.Transact;
import com.example.bank_vol_3.repository.AccountRepository;
import com.example.bank_vol_3.repository.TransactRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class TransactService {
    AccountRepository accountRepository;
    TransactRepository transactRepository;

    public void checkFields(String depositAmount, Long accountId) {
        if (depositAmount.isEmpty() || depositAmount.equals("0") || depositAmount.charAt(0) == '-') {
            throw new RuntimeException("Депозит не может быть ниже 0 или пустым");
        }
        if (accountId == null) {
            throw new RuntimeException("Необходимо выбрать аккаунт");
        }
    }

    public double getAccountBalance(Long user_id, Long account_id) {
        if (user_id == null) {
            throw new RuntimeException("Что то пошло не так");
        }

        return accountRepository.getBalance(user_id, account_id);
    }

    public void updateBalance(double currentBalance, String depositAmount, Long accountId) {
        double depositValue = Double.parseDouble(depositAmount);
        double newBalance = currentBalance + depositValue;

        accountRepository.changeAccountBalance(newBalance, accountId);
    }

    public void saveTransact(long accountId,
                             String transactionType,
                             double amount,
                             String source,
                             String status,
                             String reasonCode) {
        Transact transact = Transact.builder()
                .account_id(accountId)
                .transaction_type(transactionType)
                .amount(amount)
                .source(source)
                .status(status)
                .reason_code(reasonCode)
                .created_at(LocalDateTime.now())
                .build();

        transactRepository.save(transact);
    }

}
