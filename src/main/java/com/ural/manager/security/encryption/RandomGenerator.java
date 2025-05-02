package com.ural.manager.security.encryption;

import java.security.SecureRandom;

public interface RandomGenerator {
    /**
     * @param arrayLength необходимая длинна массива
     * @return Возвращает случайную последовательность байт в виде массива байт
     */
    byte[] getRandomBytes(int arrayLength);
}
