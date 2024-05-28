package com.example.dishplanet.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Slf4j

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword1 = "12345";
        String encodedPassword1 = encoder.encode(rawPassword1);
        System.out.println("Encoded password for '12345': " + encodedPassword1);
    log.info(encodedPassword1);
        String rawPassword2 = "user";
        String encodedPassword2 = encoder.encode(rawPassword2);
        System.out.println("Encoded password for 'user': " + encodedPassword2);
    }
}