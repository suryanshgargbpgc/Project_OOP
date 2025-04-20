package com.pharmacy.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for security-related operations
 */
public class SecurityUtil {
    
    private static final Logger logger = Logger.getLogger(SecurityUtil.class.getName());
    
    // AES encryption constants
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORM = "AES/CBC/PKCS5Padding";
    private static final int AES_KEY_SIZE = 256;
    
    // Hashing constants
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_512 = "SHA-512";
    
    /**
     * Generate a secure random AES key
     * 
     * @return The generated key as a Base64 encoded string
     */
    public static String generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGen.init(AES_KEY_SIZE);
            SecretKey key = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error generating AES key: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Generate a random initialization vector for AES encryption
     * 
     * @return The generated IV as a Base64 encoded string
     */
    public static String generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }
    
    /**
     * Encrypt data using AES
     * 
     * @param data The data to encrypt
     * @param keyBase64 The encryption key as a Base64 encoded string
     * @param ivBase64 The initialization vector as a Base64 encoded string
     * @return The encrypted data as a Base64 encoded string
     * @throws GeneralSecurityException if encryption fails
     */
    public static String encryptAES(String data, String keyBase64, String ivBase64) throws GeneralSecurityException {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
            byte[] ivBytes = Base64.getDecoder().decode(ivBase64);
            
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            
            Cipher cipher = Cipher.getInstance(AES_TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            
            byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "Error during encryption: " + e.getMessage(), e);
            throw new GeneralSecurityException("Encryption failed", e);
        }
    }
    
    /**
     * Decrypt data using AES
     * 
     * @param encryptedDataBase64 The encrypted data as a Base64 encoded string
     * @param keyBase64 The encryption key as a Base64 encoded string
     * @param ivBase64 The initialization vector as a Base64 encoded string
     * @return The decrypted data
     * @throws GeneralSecurityException if decryption fails
     */
    public static String decryptAES(String encryptedDataBase64, String keyBase64, String ivBase64) throws GeneralSecurityException {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
            byte[] ivBytes = Base64.getDecoder().decode(ivBase64);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedDataBase64);
            
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            
            Cipher cipher = Cipher.getInstance(AES_TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "Error during decryption: " + e.getMessage(), e);
            throw new GeneralSecurityException("Decryption failed", e);
        }
    }
    
    /**
     * Hash data using SHA-256
     * 
     * @param data The data to hash
     * @return The hash as a hexadecimal string
     */
    public static String hashSHA256(String data) {
        return hashData(data, SHA_256);
    }
    
    /**
     * Hash data using SHA-512
     * 
     * @param data The data to hash
     * @return The hash as a hexadecimal string
     */
    public static String hashSHA512(String data) {
        return hashData(data, SHA_512);
    }
    
    /**
     * Generic method to hash data with specified algorithm
     * 
     * @param data The data to hash
     * @param algorithm The hashing algorithm to use
     * @return The hash as a hexadecimal string
     */
    private static String hashData(String data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(data.getBytes("UTF-8"));
            
            // Convert bytes to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "Error hashing data: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Generate a random password
     * 
     * @param length The length of the password
     * @return The generated password
     */
    public static String generateRandomPassword(int length) {
        if (length < 8) {
            length = 8; // Minimum recommended length
        }
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        
        return password.toString();
    }
    
    /**
     * Check if a password meets security requirements
     * 
     * @param password The password to check
     * @return true if the password is secure, false otherwise
     */
    public static boolean isPasswordSecure(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }
        
        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }
} 