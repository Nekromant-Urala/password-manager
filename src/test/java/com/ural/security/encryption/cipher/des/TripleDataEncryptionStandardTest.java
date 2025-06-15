package com.ural.security.encryption.cipher.des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class TripleDataEncryptionStandardTest {
    private byte[] convertHexToByte(String input) {
        input = input.replaceAll("\\s", "");
        byte[] result = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(input.substring(i, i + 2), 16);
        }
        return result;
    }

    private byte[] getEncryptedText(byte[] plainText, byte[] key, byte[] iv) throws Exception {
        TripleDataEncryptionStandard des = new TripleDataEncryptionStandard();
        SecretKey secretKey = new SecretKeySpec(key, "DESede");
        return des.encrypt(plainText, secretKey, iv);
    }


    @Test
    @DisplayName("Проверка при передаче пустых данных для шифрования")
    void testEmptyText() throws Exception {
        byte[] key = convertHexToByte(
                "4C 97 3D BC 73 64 62 16 " +
                        "74 F8 B5 B8 9E 5C 15 51" +
                        "1F CE D9 21 64 90 FB 1C");
        byte[] iv = convertHexToByte("4E C5 00 01 2E 58 49 5C");
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
        byte[] key = new byte[24];
        byte[] iv = new byte[8];

        TripleDataEncryptionStandard des = new TripleDataEncryptionStandard();
        SecretKey secretKey = new SecretKeySpec(key, "DESede");

        byte[] encrypted = des.encrypt(plainText, secretKey, iv);
        byte[] decrypted = des.decrypt(encrypted, secretKey, iv);

        Assertions.assertArrayEquals(plainText, decrypted);
    }
}
