package com.ural.security.generator.passsword;

import com.ural.manager.model.PasswordConfiguration;
import com.ural.security.generator.Generator;

import java.security.SecureRandom;
import java.util.LinkedList;

import static com.ural.security.generator.passsword.Symbols.*;

public class GeneratorPassword extends Generator {
    public String generatePassword(PasswordConfiguration configuration) {
        LinkedList<Integer> numberOfChars = generateRandomNumber(configuration.getLength(), configuration.getEnabledOptions());
        StringBuilder password = new StringBuilder(configuration.getLength());
        if (configuration.isDigits() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(DIGIT.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isLowerCase() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(LOWERCASE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isUpperCase() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(UPPERCASE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isMinus() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(MINUS.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isSpace() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(SPACE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isStaples() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(STAPLE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isUnderscore() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(UNDERSCORE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isSpecialSymbols() && !numberOfChars.isEmpty()) {
            password.append(generateRandomString(SPECIAL.getSymbols(), numberOfChars.poll()));
        }

        return shuffle(password.toString());
    }

    private LinkedList<Integer> generateRandomNumber(int upperLimit, int parts) {
        SecureRandom random = new SecureRandom();
        LinkedList<Integer> result = new LinkedList<>();
        int base = upperLimit / parts;
        int remaining = upperLimit % parts;

        for (int i = 0; i < parts; i++) {
            result.add(base);
        }
        for (int i = 0; i < remaining; i++) {
            int idx = random.nextInt(parts);
            result.set(idx, result.get(idx) + 1);
        }
        return result;
    }
}
