package manager.security.key.encryption.aes;

import manager.storage.SaveData;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import static manager.security.key.encryption.aes.Specification.*;

public class AdvancedEncryptionStandard256 {


    /**
     * Метод для шифрования заданной строки.
     *
     * @param strToEncrypt строка, которую необходимо зашифровать.
     * @param password ключевое слово для генерации ключа.
     * @return Возвращает зашифрованную строку в кодировке Base64.
     */
    public static String encrypt(String strToEncrypt, String password) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            SecretKeySpec secretKey = createSecretKey(password);
            Cipher cipher = Cipher.getInstance(AES_MODE.getSpec());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] cipherText = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedData = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encryptedData, 0, iv.length);
            System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);

            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Метод для дешифрования зашифрованной строки.
     *
     * @param strToDecrypt зашифрованная строка.
     * @param password ключевое слово для генерации ключа алгоритма.
     * @return Возвращает дешифрованную строку.
     */
    public static String decrypt(String strToDecrypt, String password) {
        try {
            byte[] encryptedData = Base64.getDecoder().decode(strToDecrypt);
            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, 0, iv, 0, iv.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            SecretKeySpec secretKey = createSecretKey(password);
            Cipher cipher = Cipher.getInstance(AES_MODE.getSpec());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            byte[] cipherText = new byte[encryptedData.length - 16];
            System.arraycopy(encryptedData, 16, cipherText, 0, cipherText.length);

            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для создания ключа необходимого при работе алгоритма AES.
     *
     * @param password ключевое слово для генерации ключа алгоритма.
     * @return Возвращает ключ для дальнейшей работы алгоритма AES
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKeySpec createSecretKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Base64.getDecoder().decode(SaveData.getSaltFromFile());
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2WithHmacSHA256.getSpec());
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT.getNumber(), KEY_LENGTH.getNumber());
        javax.crypto.SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), AES.getSpec());
    }
}
