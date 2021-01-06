package com.example.project.utility;

import com.example.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {
    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user, String password) {
        String url = contextPath = "/NewUser?token="+token;
        String message = "\nKliknij w ten link aby zweryfikować twój email. Twoje hasło to: " +password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Nowy uzytkownik");
        email.setText(url+message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}
