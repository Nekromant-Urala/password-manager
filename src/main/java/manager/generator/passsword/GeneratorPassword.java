package manager.generator.passsword;

import manager.generator.Generator;

import static manager.generator.passsword.Symbols.*;

public class GeneratorPassword extends Generator {

    /**
     * Метод для того, чтобы вернуть сгенерированный пароль пользователю.
     *
     * @param generationParameters параметры принятые от пользователя
     * @return Возвращает сгенерированный пароль в формате строки.
     */
    public String getPassword(int[] generationParameters) {
        int digit = generationParameters[0];
        int lowerCase = generationParameters[1];
        int upperCase = generationParameters[2];
        int specialSymbol = generationParameters[3];
        int punctuationSymbol = generationParameters[4];
        int passwordLength = generationParameters[5];

        return generate(digit, lowerCase, upperCase, specialSymbol, punctuationSymbol, passwordLength);
    }

    /**
     * Метод для генерации паролей для пользователя.
     *
     * @param digit             количество цифр
     * @param lowerCase         количество строчных символов
     * @param upperCase         количество заглавных символов
     * @param specialSymbol     количество специальных символов
     * @param punctuationSymbol количество символов пунктуации
     * @param passwordLength    длина пароля
     * @return Возвращает пароль в формате строки.
     */
    private static String generate(int digit, int lowerCase, int upperCase, int specialSymbol, int punctuationSymbol, int passwordLength) {
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
}
