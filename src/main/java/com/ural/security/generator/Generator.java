package com.ural.security.generator;

import java.security.SecureRandom;

public abstract class Generator {

    /**
     * Метод для случайной выборки из строки заданного числа символов.
     *
     * @param inputString Входная строка, из которой необходимо получить случайные символы.
     * @param quantity    Количество символов, которое необходимо, чтобы присутствовало в пароле
     * @return Возвращает последовательность символов в формате строки.
     */
    protected static String generateRandomString(String inputString, int quantity) {
        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("Invalid inputString");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        StringBuilder symbols = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            int idx = new SecureRandom().nextInt(inputString.length());
            symbols.append(inputString.charAt(idx));
        }

        return symbols.toString();    }

    /**
     * Метод для перешивания последовательности в случайном порядке.
     *
     * @param password пароль, который нужно перемешать
     * @return Возвращает перемешанную строку.
     */
    protected static String shuffle(String password) {
        char[] result = password.toCharArray();
        for (int i = 0; i < result.length; i++) {
            int idx = new SecureRandom().nextInt(result.length - i);
            char temp = result[i];
            result[i] = result[idx];
            result[idx] = temp;
        }

        return new String(result);
    }
}
