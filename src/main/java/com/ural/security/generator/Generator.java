package com.ural.security.generator;

import java.security.SecureRandom;

public abstract class Generator {
    private static final SecureRandom secureRandom = new SecureRandom();

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
            int idx = secureRandom.nextInt(inputString.length());
            symbols.append(inputString.charAt(idx));
        }

        return symbols.toString();
    }

    /**
     * Метод для случайной выборки из строки заданного числа символов.
     *
     * @param inputString  Входная строка, из которой необходимо получить случайные символы.
     * @param quantity     Количество символов, которое необходимо, чтобы присутствовало в пароле
     * @param removeRepeat Метка, которая позволяет убрать повторяющиеся символы из генерированной строки
     * @return Возвращает последовательность символов в формате строки.
     */
    protected static String generateRandomString(String inputString, int quantity, boolean removeRepeat) {
        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("Invalid inputString");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        StringBuilder symbols = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            int idx = secureRandom.nextInt(inputString.length());
            if (removeRepeat) {
                if (inputString.length() < quantity) {
                    return inputString;
                }
                while (symbols.indexOf(String.valueOf(inputString.charAt(idx))) != -1) {
                    idx = secureRandom.nextInt(inputString.length());
                }
            }
            symbols.append(inputString.charAt(idx));
        }

        return symbols.toString();
    }

    /**
     * Метод для случайной выборки из строки заданного числа символов
     *
     * @param inputString   Входная строка, из которой необходимо получить случайные символы
     * @param quantity      Количество символов, которое необходимо, чтобы присутствовало в пароле
     * @param removeSymbols Строка символов, которые необходимо удалить при генерации
     * @return Возвращает последовательность символов в формате строки
     */
    protected static String generateRandomString(String inputString, int quantity, String removeSymbols) {
        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("Invalid inputString");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        StringBuilder symbols = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            int idx = secureRandom.nextInt(inputString.length());
            if (!inputString.equals(removeSymbols)) {
                while (removeSymbols.contains(String.valueOf(inputString.charAt(idx)))) {
                    idx = secureRandom.nextInt(inputString.length());
                }
                symbols.append(inputString.charAt(idx));
            }
        }

        return symbols.toString();
    }

    /**
     * Метод для перешивания последовательности в случайном порядке.
     *
     * @param password пароль, который нужно перемешать
     * @return Возвращает перемешанную строку.
     */
    protected static String shuffle(String password) {
        char[] result = password.toCharArray();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < result.length; i++) {
                int idx = secureRandom.nextInt(result.length - i);
                char temp = result[i];
                result[i] = result[idx];
                result[idx] = temp;
            }
        }

        return new String(result);
    }
}
