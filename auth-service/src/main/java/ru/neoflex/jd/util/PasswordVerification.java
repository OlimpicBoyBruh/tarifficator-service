package ru.neoflex.jd.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.neoflex.jd.exception.InvalidPasswordException;

@Component
@RequiredArgsConstructor
public class PasswordVerification {
    private final PasswordEncoder passwordEncoder;

    public void verify(String password, String hash) {
        if (!passwordEncoder.matches(password, hash)) {
            throw new InvalidPasswordException("Неверный логин или пароль");
        }
    }
}
