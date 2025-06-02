package com.ural.security.encryption.cipher.twofish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TwofishTest {

    private byte[] convertHexToByte(String input) {
        input = input.replaceAll("\\s", "");
        byte[] result = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(input.substring(i, i + 2), 16);
        }
        return result;
    }

    private byte[] getEncryptedText(byte[] plainText, byte[] key, byte[] iv) throws Exception {
        Twofish twofish = new Twofish();
        SecretKey secretKey = new SecretKeySpec(key, "Twofish");
        byte[] encryptedText = twofish.encrypt(plainText, secretKey, iv);
        return Arrays.copyOfRange(encryptedText, 0, encryptedText.length - 16);
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

        Twofish twofish = new Twofish();
        SecretKey secretKey = new SecretKeySpec(key, "Twofish");

        byte[] encrypted = twofish.encrypt(plainText, secretKey, iv);
        byte[] decrypted = twofish.decrypt(encrypted, secretKey, iv);

        Assertions.assertArrayEquals(plainText, decrypted);
    }

}
