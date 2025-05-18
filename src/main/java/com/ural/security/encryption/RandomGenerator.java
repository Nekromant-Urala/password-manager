package com.ural.security.encryption;

@FunctionalInterface
public interface RandomGenerator {
    /**
     * @param arrayLength необходимая длинна массива
     * @return Возвращает случайную последовательность байт в виде массива байт
     */
    byte[] getRandomBytes(int arrayLength);
}
