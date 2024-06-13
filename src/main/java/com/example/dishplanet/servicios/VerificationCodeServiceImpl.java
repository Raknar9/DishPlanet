package com.example.dishplanet.servicios;

import com.example.dishplanet.repositorios.VerificationCodeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación del servicio de códigos de verificación.
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private final Map<String, String> verificationCodes = new HashMap<>();

    /**
     * Guarda un código de verificación para un usuario específico.
     *
     * @param username Nombre de usuario para el cual se guardará el código de verificación.
     * @param code     Código de verificación a guardar.
     */
    @Override
    public void saveVerificationCode(String username, String code) {
        verificationCodes.put(username, code);
    }

    /**
     * Verifica si el código de verificación proporcionado es válido para el usuario específico.
     *
     * @param username Nombre de usuario para el cual se verificará el código de verificación.
     * @param code     Código de verificación a verificar.
     * @return `true` si el código de verificación es válido, `false` de lo contrario.
     */
    @Override
    public boolean isCodeValid(String username, String code) {
        String savedCode = verificationCodes.get(username);
        return savedCode != null && savedCode.equals(code);
    }
}