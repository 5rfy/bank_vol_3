package com.example.bank_vol_3.service;

import com.example.bank_vol_3.entities.User;
import com.example.bank_vol_3.helper.HTML;
import com.example.bank_vol_3.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    MailService mailService;
    PasswordEncoder passwordEncoder;

    public void registerUser(String firstname, String lastname, String email, String password) {
        var existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException(); //TODO: create exception
        }
        UUID token = generateToken();
        int code = generateCode();

        User user = User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .token(token)
                .code(code)
                .verified(0)
                .created_at(LocalDateTime.now())
                .build();
        userRepository.save(user);
        mailService.send(email, "Подтверждение аккаунта", HTML.htmlEmailTemplate(token.toString(), Integer.toString(code)));
    }

    public void verifyAccount(UUID token, Integer code) {
        String dbToken = userRepository.checkToken(token);

        if (dbToken == null) {
            throw new RuntimeException(); //TODO: create exception
        }

        userRepository.verifyAccount(token, code);
    }

    public void authentication(String email, String password, UUID token, HttpSession session) {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("Поля почты и пароля должны быть заполнены");
        }

        Optional<User> dbUser = userRepository.findByEmail(email);

        if (dbUser.isEmpty()) {
            throw new RuntimeException("Пользователя с такой почтой не существует"); //TODO: create exception
        }

        String dbEmail = dbUser.get().getEmail();
        boolean checkPass = BCrypt.checkpw(password, dbUser.get().getPassword());

        if (!(dbEmail.equals(email) && checkPass)) {
            throw new RuntimeException("Почта или пароль введены не верно"); //TODO: create exception
        }

        int dbVerified = userRepository.isVerified(email);

        if (dbVerified != 1) {
            throw new RuntimeException("Данный аккаунт не активирован"); //TODO: create exception
        }

        User user = dbUser.get();
        session.setAttribute("user", user);
        session.setAttribute("token", token);
        session.setAttribute("authenticated", true);

    }

    private int generateCode() {
        Random rand = new Random();
        int bound = 123;
        return bound * rand.nextInt(bound);
    }

    public static UUID generateToken() {
        return UUID.randomUUID();
    }
}
