package com.ural.manager.service;

import com.ural.manager.model.Database;
import com.ural.manager.model.MasterPasswordHolder;
import com.ural.manager.model.MetaData;
import com.ural.manager.model.PasswordEntre;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.EncryptionService;
import com.ural.security.encryption.service.KeyGeneratorFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PasswordEntreService {
    private final DatabaseService databaseService;
    private final EncryptionService encryptionService;
    private final Path pathFile;
    private final Database database;

    public PasswordEntreService() {
        this.databaseService = new DatabaseService();
        JsonFileStorage fileStorage = new JsonFileStorage();
        this.pathFile = Paths.get(fileStorage.loadPaths().get(0));
        database = databaseService.loadDatabase(pathFile);
        MetaData metaData = database.getMetaData();
        this.encryptionService = new EncryptionService(
                CipherFactory.getNameCipher(metaData.getEncryptAlgorithm()),
                KeyGeneratorFactory.getKeyGenerator(metaData.getKeyGenerator()),
                metaData.getIterations()
        );
    }

    public void addPasswordEntre(PasswordEntre passwordEntre) {
        byte[] encryptedPassword;
        try {
            encryptedPassword = encryptionService.encrypt(
                    passwordEntre.getPassword().getBytes(StandardCharsets.UTF_8),
                    MasterPasswordHolder.getMasterPassword()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PasswordEntre entre = new PasswordEntre.Builder(passwordEntre)
                .addPassword(new String(encryptedPassword, StandardCharsets.UTF_8))
                .build();

        database.getEntries().add(entre);
        databaseService.saveChanges(pathFile, database);
    }

    public void deletePasswordEntre(PasswordEntre passwordEntre) {
        Database database = databaseService.loadDatabase(pathFile);
        database.getEntries().remove(passwordEntre);
        databaseService.saveChanges(pathFile, database);
    }


    public List<PasswordEntre> getAllElements() {
        Database database = databaseService.loadDatabase(pathFile);
        return database.getEntries();
    }
}
