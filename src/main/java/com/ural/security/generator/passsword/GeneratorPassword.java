package com.ural.security.generator.passsword;

import com.ural.manager.model.PasswordConfiguration;
import com.ural.security.generator.Generator;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Map;


public class GeneratorPassword extends Generator {
    public String generatePassword(PasswordConfiguration configuration) {
        LinkedList<Integer> numberOfChars = generateRandomNumber(configuration.getLength(), configuration.getEnabledOptions());
        StringBuilder password = new StringBuilder(configuration.getLength());

        for (Map.Entry<Symbols, Boolean> symbols : configuration.getMap().entrySet()) {

            if (symbols.getValue() && !numberOfChars.isEmpty()) {
                int count = numberOfChars.pollFirst();
                String symb = symbols.getKey().getSymbols();
                if (configuration.isRemoveSymbols()) {
                    password.append(generateRandomString(symb, count, configuration.getRemoveSymbols()));
                } else {
                    System.out.println(configuration.isRemoveRepetitions());
                    password.append(generateRandomString(symb, count, configuration.isRemoveRepetitions()));
                }
            }
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
