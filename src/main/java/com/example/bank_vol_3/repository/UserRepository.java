package com.example.bank_vol_3.repository;

import com.example.bank_vol_3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE users SET token=null, code=null, verified=1, verified_at=now(), updated_at=now() WHERE token= :token AND code= :code", nativeQuery = true)
    @Transactional
    void verifyAccount(@Param("token") UUID token,
                       @Param("code") Integer code);

    @Query(value = "SELECT token FROM users WHERE token= :token", nativeQuery = true)
    String checkToken(@Param("token") UUID token);

    @Query(value = "SELECT verified FROM users WHERE email= :email", nativeQuery = true)
    int isVerified(@Param("email") String email);
}