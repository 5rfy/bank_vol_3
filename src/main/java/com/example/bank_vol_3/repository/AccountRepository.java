package com.example.bank_vol_3.repository;

import com.example.bank_vol_3.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT * FROM accounts WHERE user_id =:user_id", nativeQuery = true)
    List<Account> findAccountsById(@Param("user_id") Long id);

    @Query(value = "SELECT sum(balance) FROM accounts WHERE user_id =:user_id", nativeQuery = true)
    BigDecimal getTotalBalance(@Param("user_id") Long id);

    @Query(value = "SELECT balance FROM accounts WHERE user_id =:user_id AND account_id = :account_id", nativeQuery = true)
    double getBalance(@Param("user_id") Long user_id, @Param("account_id") Long account_id);

    @Modifying
    @Query(value = "UPDATE accounts SET balance = :new_balance WHERE account_id= :account_id", nativeQuery = true)
    @Transactional
    void changeAccountBalance(@Param("new_balance") double new_balance, @Param("account_id") Long account_id);

    @Modifying
    @Query(value = "INSERT INTO accounts(user_id, account_number, account_name, account_type, balance, created_at) VALUES " +
            "(:user_id, :account_number, :account_name, :account_type, 0, now())", nativeQuery = true)
    @Transactional
    void createBankAccount(@Param("user_id") Long id,
                           @Param("account_number") Integer accountNumber,
                           @Param("account_name") String accountName,
                           @Param("account_type") String accountType);
    //TODO: изменить на метод save через builder

    Account findAccountByAccountNumber(int accountNumber);
}