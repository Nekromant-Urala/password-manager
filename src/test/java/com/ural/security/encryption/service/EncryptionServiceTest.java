package com.ural.security.encryption.service;

import com.ural.security.encryption.spec.CipherAlgorithm;
import com.ural.security.encryption.spec.KeyGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.crypto.BadPaddingException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;


public class EncryptionServiceTest {
    private static final char[] MASTER_PASSWORD = "password".toCharArray();
    private static final byte[] TEST_DATA = "test".getBytes(StandardCharsets.UTF_8);

    private static Stream<Arguments> supportedAlgorithmsPBKDF2() {
        return Stream.of(
                Arguments.of(CipherAlgorithm.AES_MODE, KeyGenerator.PBKDF2),
                Arguments.of(CipherAlgorithm.TWOFISH_MODE, KeyGenerator.PBKDF2),
                Arguments.of(CipherAlgorithm.CHACHA20_MODE, KeyGenerator.PBKDF2)
        );
    }

    private static Stream<Arguments> supportedAlgorithmsArgon2() {
        return Stream.of(
                Arguments.of(CipherAlgorithm.AES_MODE, KeyGenerator.ARGON2),
                Arguments.of(CipherAlgorithm.TWOFISH_MODE, KeyGenerator.ARGON2),
                Arguments.of(CipherAlgorithm.CHACHA20_MODE, KeyGenerator.ARGON2)
        );
    }

    @ParameterizedTest
    @MethodSource("supportedAlgorithmsPBKDF2")
    @DisplayName("Проверка алгоритмов шифрования с генератором PBKDF2")
    void testAlgorithmsWithPBKDF2(CipherAlgorithm algorithm, KeyGenerator keyGenerator) throws Exception {
        EncryptionService service = new EncryptionService(algorithm, keyGenerator, 30_000);

        byte[] encrypted = service.encrypt(TEST_DATA, MASTER_PASSWORD);
        byte[] decrypted = service.decrypt(encrypted, MASTER_PASSWORD);

        Assertions.assertArrayEquals(TEST_DATA, decrypted);
    }

    @ParameterizedTest
    @MethodSource("supportedAlgorithmsArgon2")
    @DisplayName("Проверка алгоритмов шифрования с генератором Argon2")
    void testAlgorithmsWithArgon2(CipherAlgorithm algorithm, KeyGenerator keyGenerator) throws Exception {
        EncryptionService service = new EncryptionService(algorithm, keyGenerator, 5);

        byte[] encrypted = service.encrypt(TEST_DATA, MASTER_PASSWORD);
        byte[] decrypted = service.decrypt(encrypted, MASTER_PASSWORD);

        Assertions.assertArrayEquals(TEST_DATA, decrypted);
    }

    @Test
    @DisplayName("Проверка при передачи null в качестве значения данных")
    void testEncryptNullData() {
        EncryptionService service = new EncryptionService(CipherAlgorithm.AES_MODE, KeyGenerator.PBKDF2, 30_000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.encrypt(null, MASTER_PASSWORD));
    }

    @Test
    @DisplayName("Проверка при расшифровывании неправильным паролем")
    void testDecryptWithWrongPassword() throws Exception {
        EncryptionService service = new EncryptionService(CipherAlgorithm.AES_MODE, KeyGenerator.PBKDF2, 30_000);

        byte[] encrypted = service.encrypt(TEST_DATA, MASTER_PASSWORD);

        Assertions.assertThrows(BadPaddingException.class, () -> service.decrypt(encrypted, "wrongPassword".toCharArray()));
    }
}
