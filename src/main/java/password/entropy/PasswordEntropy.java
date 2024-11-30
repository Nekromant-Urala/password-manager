package password.entropy;

import static password.entropy.Symbols.*;
import static password.entropy.PasswordCategory.*;

public class PasswordEntropy {

    /**
     * @param quantityDigit             количество цифр
     * @param quantityLowerCase         количество строчных символов
     * @param quantityUpperCase         количество заглавных символов
     * @param quantitySpecialSymbol     количество специальных символов
     * @param quantityPunctuationSymbol количество символов пунктуации
     * @param passwordLength            длина пароля
     * @return Возвращает категорию сложности заданного пароля
     */
    public static String getEntropy(int quantityDigit, int quantityLowerCase, int quantityUpperCase, int quantitySpecialSymbol, int quantityPunctuationSymbol, int passwordLength) {
        int countCharacters = 0;
        if (quantityDigit > 0) {
            countCharacters += DIGIT.gatNumberOfCharacters();
        }

        if (quantityLowerCase > 0) {
            countCharacters += CHAR_LOWERCASE.gatNumberOfCharacters();
        }

        if (quantityUpperCase > 0) {
            countCharacters += CHAR_UPPERCASE.gatNumberOfCharacters();
        }

        if (quantityPunctuationSymbol > 0) {
            countCharacters += PUNCTUATION_SYMBOL.gatNumberOfCharacters();
        }

        if (quantitySpecialSymbol > 0) {
            countCharacters += SPECIAL_SYMBOL.gatNumberOfCharacters();
        }

        double entropy = passwordLength * (Math.log(countCharacters) / Math.log(2));

        return getCategoryEntropy(entropy);
    }

    /**
     *
     * @param entropy Посчитанная энтропия заданного пароля
     * @return Возвращает категорию сложности пароля в формате строки
     */
    private static String getCategoryEntropy(double entropy) {
        if (75 <= entropy) {
            return VERY_GOOD.getCategory();
        }
        if (50.0 <= entropy & entropy <= 74.0) {
            return REASONABLE.getCategory();
        }
        if (25.0 <= entropy & entropy <= 49.0) {
            return WEAK.getCategory();
        }
        return POOR.getCategory();
    }
}
