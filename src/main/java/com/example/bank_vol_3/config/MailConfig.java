package com.example.bank_vol_3.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    String host;
    @Value("${spring.mail.port}")
    int port;
    @Value("${spring.mail.username}")
    String username;
    @Value("${spring.mail.password}")
    String password;
    @Value("${spring.mail.protocol}")
    String protocol;
    @Value("${spring.mail.debug}")
    String debug;


    @Bean
    public JavaMailSender getMailConfig() {
        JavaMailSenderImpl emailConfig = new JavaMailSenderImpl();

        emailConfig.setHost(host);
        emailConfig.setPort(port);
        emailConfig.setUsername(username);
        emailConfig.setPassword(password);

        Properties properties = emailConfig.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return emailConfig;
    }
}
