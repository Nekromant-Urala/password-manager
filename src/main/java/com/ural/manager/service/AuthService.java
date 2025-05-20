package com.ural.manager.service;

import com.ural.manager.model.Database;
import com.ural.manager.model.MetaData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.EncryptionService;
import com.ural.security.encryption.service.KeyGeneratorFactory;

import java.util.Arrays;
import java.util.List;

public class AuthService {
    private final JsonFileStorage fileStorage;
    private final DatabaseService databaseService;

    public AuthService() {
        this.fileStorage = new JsonFileStorage();
        this.databaseService = new DatabaseService();
    }

    public boolean verifyPassword(String inputPassword) {
        Database database = databaseService.loadDatabase(fileStorage.loadPaths().get(0));
        MetaData metaData = database.getMetaData();
        // получаем данные, из json для генерации хеша для сравнения
        EncryptionService encryptionService = new EncryptionService(
                CipherFactory.getNameCipher(metaData.getEncryptAlgorithm()),
                KeyGeneratorFactory.getKeyGenerator(metaData.getKeyGenerator()),
                metaData.getIterations()
        );

        List<byte[]> meta = encryptionService.getSaltAndNonceFromMeta(metaData.getEncryptedPasswordWithMeta());
        byte[] encryptedPassword = new byte[0];
        try {
            encryptedPassword = encryptionService.encrypt(inputPassword, meta.get(0), meta.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.equals(meta.get(2), encryptedPassword);
    }
}
