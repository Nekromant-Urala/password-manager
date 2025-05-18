package com.ural.security.generator.passsword;

import com.ural.manager.model.PasswordConfiguration;
import com.ural.security.generator.Generator;

import java.security.SecureRandom;
import java.util.LinkedList;

import static com.ural.security.generator.passsword.Symbols.*;

public class GeneratorPassword extends Generator {

    public String generatePassword(PasswordConfiguration configuration) {
        LinkedList<Integer> numberOfChars = generateRandomNumber(configuration.getLength(), configuration.getCount());
        StringBuilder password = new StringBuilder(configuration.getLength());
        if (configuration.isDigits()) {
            password.append(generateRandomString(DIGIT.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isLowerCase()) {
            password.append(generateRandomString(LOWERCASE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isUpperCase()) {
            password.append(generateRandomString(UPPERCASE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isMinus()) {
            password.append(generateRandomString(MINUS.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isSpace()) {
            password.append(generateRandomString(SPACE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isStaples()) {
            password.append(generateRandomString(STAPLE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isUnderscore()) {
            password.append(generateRandomString(UNDERSCORE.getSymbols(), numberOfChars.poll()));
        }
        if (configuration.isSpecialSymbols()) {
            password.append(generateRandomString(SPECIAL.getSymbols(), numberOfChars.poll()));
        }

        return shuffle(password.toString());
    }

    private LinkedList<Integer> generateRandomNumber(int upperLimit, int parts) {
        SecureRandom random = new SecureRandom();
        LinkedList<Integer> nums = new LinkedList<>();
        int remaining = upperLimit;
        for (int i = 0; i < parts - 1; ++i) {
            int max = remaining - (parts - 1);
            if (max <= 0) {
                nums.add(1);
                remaining -= 1;
            } else {
                int next = random.nextInt(max) + 1;
                nums.add(next);
                remaining -= next;
            }
        }
        nums.add(remaining);
        return nums;
    }
}
