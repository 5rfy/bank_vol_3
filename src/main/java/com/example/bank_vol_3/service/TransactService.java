package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.Transact;
import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.repository.AccountRepository;
import com.example.bank_vol_3.repository.TransactRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class TransactService {
    AccountRepository accountRepository;
    TransactRepository transactRepository;

    private void checkFields(BigDecimal depositAmount, Long accountId) {
        if (depositAmount.toString().isEmpty() || depositAmount.toString().equals("0") || depositAmount.toString().charAt(0) == '-') {
            throw new RuntimeException("Депозит не может быть ниже 0 или пустым");
        }
        if (accountId == null) {
            throw new RuntimeException("Необходимо выбрать аккаунт");
        }
    }

    public BigDecimal getAccountBalance(Long user_id, Long account_id) {
        if (user_id == null) {
            throw new RuntimeException("Что то пошло не так");
        }

        return accountRepository.getBalance(user_id, account_id);
    }

    public void updateBalance(User user, BigDecimal depositAmount, Long accountId) {
        checkFields(depositAmount, accountId);
        BigDecimal currentBalance = getAccountBalance(user.getId(), accountId);
        BigDecimal newBalance = currentBalance.subtract(depositAmount);

        accountRepository.changeAccountBalance(newBalance, accountId);
    }

    public void saveTransact(long accountId,
                             String transactionType,
                             BigDecimal amount,
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
