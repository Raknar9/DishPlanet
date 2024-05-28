package com.example.dishplanet.servicios;

import com.example.dishplanet.repositorios.VerificationCodeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final Map<String, String> verificationCodes = new HashMap<>();

    @Override
    public void saveVerificationCode(String username, String code) {
        verificationCodes.put(username, code);
    }

    @Override
    public boolean isCodeValid(String username, String code) {
        String savedCode = verificationCodes.get(username);
        return savedCode != null && savedCode.equals(code);
    }
}
