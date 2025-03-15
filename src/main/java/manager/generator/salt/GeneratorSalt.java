package manager.generator.salt;

import manager.generator.Generator;

import java.util.Base64;

import static manager.generator.passsword.Symbols.*;
import static manager.generator.salt.SpecificationSalt.*;

public class GeneratorSalt extends Generator {

    /**
     * Метод для того, чтобы получить сгенерированную "соль".
     *
     * @return Возвращает сгенерированную соль в формате строки.
     */
    public String getSalt() {
        return generate();
    }

    /**
     * Метод для генерации "соли", необходимая при создании ключа.
     *
     * @return Возвращает "соль" в формате строки, которая сгенерирована по заранее заданным параметрам.
     */
    private static String generate() {
        String salt = generateRandomString(DIGIT.getSymbols(), DIGIT_COUNT.getCount()) +
                generateRandomString(CHAR_UPPERCASE.getSymbols(), CHAR_UPPERCASE_COUNT.getCount()) +
                generateRandomString(CHAR_LOWERCASE.getSymbols(), CHAR_LOWERCASE_COUNT.getCount()) +
                generateRandomString(SPECIAL_SYMBOL.getSymbols(), SPECIAL_SYMBOL_COUNT.getCount()) +
                generateRandomString(PUNCTUATION_SYMBOL.getSymbols(), PUNCTUATION_SYMBOL_COUNT.getCount());

        return Base64.getEncoder().encodeToString(shuffle(salt).getBytes());
    }
}
