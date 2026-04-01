package com.filemasterapp.FilemasterApp.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilitaria que proporciona métodos de utilidad para operaciones comunes.
 * Incluye funciones de hash y otras operaciones auxiliares.
 */
public class Utils {

    /**
     * Calcula el hash MD5 de una cadena de texto.
     * <p>
     * Este método convierte la entrada en bytes y genera un hash MD5,
     * retornando el resultado como una cadena hexadecimal de 32 caracteres.
     * </p>
     *
     * @param input la cadena de texto a hashear
     * @return la representación hexadecimal del hash MD5
     * @throws RuntimeException si no se puede obtener el algoritmo MD5
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());

            // Convertir a hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
