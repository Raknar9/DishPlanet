package com.example.dishplanet.repositorios;

/**
 * Interface que define los métodos para gestionar códigos de verificación asociados a usuarios.
 */
public interface VerificationCodeService {

    /**
     * Guarda un código de verificación asociado a un usuario.
     *
     * @param username El nombre de usuario al que se asocia el código de verificación.
     * @param code     El código de verificación a guardar.
     */
    void saveVerificationCode(String username, String code);

    /**
     * Verifica si un código de verificación es válido para un usuario específico.
     *
     * @param username El nombre de usuario para el cual se verifica el código.
     * @param code     El código de verificación a comprobar.
     * @return true si el código es válido para el usuario dado, false en caso contrario.
     */
    boolean isCodeValid(String username, String code);
}