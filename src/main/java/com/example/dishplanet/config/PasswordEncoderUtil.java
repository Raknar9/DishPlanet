package com.example.dishplanet.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Slf4j
//Se usa para crear claves encriptadas manuealmente en caso de ser necesario
public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword1 = "12345";
        String encodedPassword1 = encoder.encode(rawPassword1);
        System.out.println("Encoded password for '12345': " + encodedPassword1);

    }
}