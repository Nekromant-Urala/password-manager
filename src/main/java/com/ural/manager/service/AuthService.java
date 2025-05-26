package com.ural.manager.service;

import com.ural.manager.status.StatusVerifyPassword;
import com.ural.manager.model.Database;
import com.ural.manager.model.MetaData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.EncryptionService;
import com.ural.security.encryption.service.KeyGeneratorFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class AuthService {
    private final JsonFileStorage fileStorage;
    private final DatabaseService databaseService;

    public AuthService() {
        this.fileStorage = new JsonFileStorage();
        this.databaseService = new DatabaseService();
    }

    public StatusVerifyPassword verifyPassword(char[] inputPassword) {
        Path path = Paths.get(fileStorage.loadPaths().get(0));
        Database database;
        try {
            database = databaseService.loadDatabase(path);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке базы данных. " + e.getMessage());
            return StatusVerifyPassword.ERROR;
        }
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
            System.err.println("Ошибка при шифровании введенного пользователем пароля");
        }
        if (Arrays.equals(meta.get(2), encryptedPassword)) {
            return StatusVerifyPassword.OK;
        } else {
            return StatusVerifyPassword.WRONG;
        }
    }
}
