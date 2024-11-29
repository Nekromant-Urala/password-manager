package password.generator;

import java.security.SecureRandom;

import static password.generator.Symbols.*;


public class Generator {
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * @param quantityDigit             количество цифр
     * @param quantityLowerCase         количество строчных символов
     * @param quantityUpperCase         количество заглавных символов
     * @param quantitySpecialSymbol     количество специальных символов
     * @param quantityPunctuationSymbol количество символов пунктуации
     * @param passwordLength            длина пароля. По умолчанию длина пароля равна 16
     * @return Возвращает пароль в виде строки
     */
    public static String generatePassword(int quantityDigit, int quantityLowerCase, int quantityUpperCase, int quantitySpecialSymbol, int quantityPunctuationSymbol, int passwordLength) {
        StringBuilder passwordBuilder = new StringBuilder(passwordLength);

        passwordBuilder.append(generateRandomString(DIGIT.getSymbols(), quantityDigit));
        passwordBuilder.append(generateRandomString(CHAR_UPPERCASE.getSymbols(), quantityUpperCase));
        passwordBuilder.append(generateRandomString(CHAR_LOWERCASE.getSymbols(), quantityLowerCase));
        passwordBuilder.append(generateRandomString(SPECIAL_SYMBOL.getSymbols(), quantitySpecialSymbol));
        passwordBuilder.append(generateRandomString(PUNCTUATION_SYMBOL.getSymbols(), quantityPunctuationSymbol));

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
        if (quantity <= 0) {
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
