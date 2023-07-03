package com.example.bank_vol_3.helper;

public class HTML {
    public static String htmlEmailTemplate(String token, String code) {

        String url = "http://localhost:8080/api/v1/verify?token=" + token + "&code=" + code;

        return "<!doctype html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport'\n" +
                "          content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>\n" +
                "    <meta http-equiv='X-UA-Compatible' content='ie=edge'>\n" +
                "    <link rel='stylesheet' href='/static/css/email.css' th:href='@{/css/email.css}'>\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class='wrapper'>\n" +
                "    <h2 class='email-msg-header'>\n" +
                "        Welcome and Thank You for Choosing\n" +
                "    </h2>\n" +
                "    <div class='company-name'>\n" +
                "        Easy Way Bank\n" +
                "    </div>\n" +
                "    <hr>\n" +
                "    <p class='welcome-text'>\n" +
                "        Your Account nas been successfully registered, please click below to verify your account\n" +
                "    </p>\n" +
                "    <br>\n" +
                "    <br>\n" +
                "    <div class='verify-btn'>\n" +
                "        <a href='" + url + "' class='verify-account-btn' role='button'>Verify Account</a>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class='copy-right'>\n" +
                "        &copy; Copy right 2023. All Rights Reserved.\n" +
                "    </div>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}
