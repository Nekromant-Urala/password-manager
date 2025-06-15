package com.ural.security.encryption.cipher.chacha20;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ChaCha20Test {
    private byte[] convertHexToByte(String input) {
        input = input.replaceAll("\\s", "");
        byte[] result = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(input.substring(i, i + 2), 16);
        }
        return result;
    }

    private byte[] getEncryptedText(byte[] plainText, byte[] key, byte[] iv) throws Exception {
        ChaCha20 chaCha20 = new ChaCha20();
        SecretKey secretKey = new SecretKeySpec(key, "ChaCha20");
        byte[] encryptedText = chaCha20.encrypt(plainText, secretKey, iv);
        return Arrays.copyOfRange(encryptedText, 0, encryptedText.length - 16);
    }

    @Test
    @DisplayName("Проверка шифрования данных")
    void testEncryptionChaCha20TestVector() throws Exception {
        byte[] key = convertHexToByte("000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f");
        byte[] iv = convertHexToByte("000000000000004a00000000");
        byte[] plainText = convertHexToByte(
                "4c 61 64 69 65 73 20 61 6e 64 20 47 65 6e 74 6c" +
                        "65 6d 65 6e 20 6f 66 20 74 68 65 20 63 6c 61 73" +
                        "73 20 6f 66 20 27 39 39 3a 20 49 66 20 49 20 63" +
                        "6f 75 6c 64 20 6f 66 66 65 72 20 79 6f 75 20 6f" +
                        "6e 6c 79 20 6f 6e 65 20 74 69 70 20 66 6f 72 20" +
                        "74 68 65 20 66 75 74 75 72 65 2c 20 73 75 6e 73" +
                        "63 72 65 65 6e 20 77 6f 75 6c 64 20 62 65 20 69" +
                        "74 2e");
        byte[] expectedCipherText = convertHexToByte(
                "6e 2e 35 9a 25 68 f9 80 41 ba 07 28 dd 0d 69 81" +
                        "e9 7e 7a ec 1d 43 60 c2 0a 27 af cc fd 9f ae 0b" +
                        "f9 1b 65 c5 52 47 33 ab 8f 59 3d ab cd 62 b3 57" +
                        "16 39 d6 24 e6 51 52 ab 8f 53 0c 35 9f 08 61 d8" +
                        "07 ca 0d bf 50 0d 6a 61 56 a3 8e 08 8a 22 b6 5e" +
                        "52 bc 51 4d 16 cc f8 06 81 8c e9 1a b7 79 37 36" +
                        "5a f9 0b bf 74 a3 5b e6 b4 0b 8e ed f2 78 5e 42" +
                        "87 4d"
                );

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

        ChaCha20 chaCha20 = new ChaCha20();
        SecretKey secretKey = new SecretKeySpec(key, "ChaCha20");

        byte[] encrypted = chaCha20.encrypt(plainText, secretKey, iv);
        byte[] decrypted = chaCha20.decrypt(encrypted, secretKey, iv);

        Assertions.assertArrayEquals(plainText, decrypted);
    }
}
