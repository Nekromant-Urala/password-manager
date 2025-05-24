package com.ural.manager.service;

import com.ural.manager.model.PasswordConfiguration;
import com.ural.security.generator.passsword.GeneratorPassword;

import java.util.ArrayList;
import java.util.List;

public class GeneratorService {
    private PasswordConfiguration configuration;
    private final GeneratorPassword generator;

    public GeneratorService() {
        this.generator = new GeneratorPassword();
    }

    public void setConfiguration(PasswordConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<String> generate(int quantity) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < quantity; ++i) {
            passwords.add(generator.generatePassword(configuration));
        }
        return passwords;
    }
}
