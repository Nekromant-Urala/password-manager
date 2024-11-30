package password.generator;

import java.security.SecureRandom;

import static password.generator.Symbols.*;


public class Generator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String getPassword(int[] generationParameters) {
        int digit = generationParameters[0];
        int lowerCase = generationParameters[1];
        int upperCase = generationParameters[2];
        int specialSymbol = generationParameters[3];
        int punctuationSymbol = generationParameters[4];
        int passwordLength = generationParameters[5];

        return generatePassword(digit, lowerCase, upperCase, specialSymbol, punctuationSymbol, passwordLength);
    }

    /**
     * @param digit             количество цифр
     * @param lowerCase         количество строчных символов
     * @param upperCase         количество заглавных символов
     * @param specialSymbol     количество специальных символов
     * @param punctuationSymbol количество символов пунктуации
     * @param passwordLength    длина пароля
     * @return Возвращает пароль в виде строки
     */
    private static String generatePassword(int digit, int lowerCase, int upperCase, int specialSymbol, int punctuationSymbol, int passwordLength) {
        StringBuilder passwordBuilder = new StringBuilder(passwordLength);

        passwordBuilder.append(generateRandomString(DIGIT.getSymbols(), digit));
        passwordBuilder.append(generateRandomString(CHAR_UPPERCASE.getSymbols(), upperCase));
        passwordBuilder.append(generateRandomString(CHAR_LOWERCASE.getSymbols(), lowerCase));
        passwordBuilder.append(generateRandomString(SPECIAL_SYMBOL.getSymbols(), specialSymbol));
        passwordBuilder.append(generateRandomString(PUNCTUATION_SYMBOL.getSymbols(), punctuationSymbol));

        if (passwordBuilder.length() < passwordLength) {
            passwordBuilder.append(generateRandomString(ALL_SYMBOLS.getSymbols(), passwordLength - passwordBuilder.length()));
        }
        String password = passwordBuilder.toString();

        return shuffle(password);
    }

    /**
     * @param inputString Входная строка, из которой необходимо получить случайные символы.
     * @param quantity    Количество символов, которое необходимо, чтобы присутствовало в пароле
     * @return Возвращает случайные символы из inputString
     */
    private static String generateRandomString(String inputString, int quantity) {
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
     * @param password пароль, который нужно перемешать
     * @return Возвращает перемешанный пароль
     */
    private static String shuffle(String password) {
        char[] result = password.toCharArray();
        for (int i = 0; i < result.length; i++) {
            int idx = secureRandom.nextInt(result.length - i);
            char temp = result[i];
            result[i] = result[idx];
            result[idx] = temp;
        }

        return new String(result);
    }
}
