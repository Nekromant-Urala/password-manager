package com.ural.security.encryption.cipher.aes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AdvancedEncryptionStandardTest {

    private byte[] convertHexToByte(String input) {
        input = input.replaceAll("\\s", "");
        byte[] result = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(input.substring(i, i + 2), 16);
        }
        return result;
    }

    private byte[] getEncryptedText(byte[] plainText, byte[] key, byte[] iv) throws Exception {
        AdvancedEncryptionStandard aes = new AdvancedEncryptionStandard();
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        byte[] encryptedText = aes.encrypt(plainText, secretKey, iv);
        return Arrays.copyOfRange(encryptedText, 0, encryptedText.length - 16);
    }

    @Test
    @DisplayName("Проверка шифрования данных")
    void testEncryptionAESTestVector() throws Exception {
        byte[] key = convertHexToByte("4C973DBC7364621674F8B5B89E5C15511FCED9216490FB1C1A2CAA0FFE0407E5");
        byte[] iv = convertHexToByte("7AE8E2CA4EC500012E58495C");
        byte[] plainText = convertHexToByte(
                "08000F101112131415161718191A1B1C1D" +
                        "1E1F202122232425262728292A2B2C2D" +
                        "2E2F303132333435363738393A3B3C3D" +
                        "3E3F404142434445464748490008");
        byte[] expectedCipherText = convertHexToByte(
                "BA8AE31BC506486D6873E4FCE460E7DC" +
                        "57591FF00611F31C3834FE1C04AD80B6" +
                        "6803AFCF5B27E6333FA67C99DA47C2F0" +
                        "CED68D531BD741A943CFF7A6713BD0");

        byte[] actualCipherText = getEncryptedText(plainText, key, iv);

        Assertions.assertArrayEquals(expectedCipherText, actualCipherText);
    }

    @Test
    @DisplayName("Проверка при передаче пустых данных для шифрования")
    void testEmptyText() throws Exception {
        byte[] key = convertHexToByte(
                "4C973DBC7364621674F8B5B89E5C1551" +
                        "1FCED9216490FB1C1A2CAA0FFE0407E5");
        byte[] iv = convertHexToByte("7AE8E2CA4EC500012E58495C");
        byte[] plainText = {};
        byte[] expectedCipherText = {};

        byte[] actualCipherText = getEncryptedText(plainText, key, iv);

        Assertions.assertArrayEquals(expectedCipherText, actualCipherText);
    }

    @Test
    @DisplayName("Проверка на шифрование и расшифровывание данных")
    void testEncryptDecryptData() throws Exception {
        byte[] plainText = """
                Я таю от твоего завораживающего взгляда,
                словно лёд под весенним теплым солнцем.
                """.getBytes(StandardCharsets.UTF_8);
        byte[] key = new byte[32];
        byte[] iv = new byte[12];

        AdvancedEncryptionStandard aes = new AdvancedEncryptionStandard();
        SecretKey secretKey = new SecretKeySpec(key, "AES");

        byte[] encrypted = aes.encrypt(plainText, secretKey, iv);
        byte[] decrypted = aes.decrypt(encrypted, secretKey, iv);

        Assertions.assertArrayEquals(plainText, decrypted);
    }
}
