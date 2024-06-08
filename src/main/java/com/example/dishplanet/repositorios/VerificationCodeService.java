package com.example.dishplanet.repositorios;

public interface VerificationCodeService {
    void saveVerificationCode(String username, String code);

    boolean isCodeValid(String username, String code);
}