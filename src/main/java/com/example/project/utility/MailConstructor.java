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

    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        String message = "\nDziękujemy za zarejestrowanie się!";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Nowy uzytkownik");
        email.setText(message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public SimpleMailMessage constructResetTokenEmailReset(String contextPath, Locale locale, String token, User user, String password) {
        String url = contextPath + "/newUser?token="+token;
        String message = "\nKliknij w ten link aby zmienić swoje hasło. Twoje hasło to: " +password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Zapomniałeś hasła");
        email.setText(url+message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}
