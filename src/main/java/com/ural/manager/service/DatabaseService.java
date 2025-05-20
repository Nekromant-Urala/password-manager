package com.ural.manager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ural.manager.model.Database;
import com.ural.manager.model.MetaData;
import com.ural.manager.model.SettingsData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.manager.serialization.JsonSerializer;
import com.ural.manager.serialization.Serializer;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.KeyGeneratorFactory;
import com.ural.security.encryption.service.EncryptionService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DatabaseService {
    private final Serializer serializer;
    private final JsonFileStorage fileStorage;

    public DatabaseService() {
        this.serializer = new JsonSerializer();
        this.fileStorage = new JsonFileStorage();
    }

    public void createDatabase(SettingsData settings) {
        try {
            Path path = fileStorage.getAbsolutePath(settings.getPathFile(), settings.getNameFile());
            FileUtils.createFileIfNotExists(path);
            EncryptionService encryptionService = new EncryptionService(
                    CipherFactory.getNameCipher(settings.getEncryptAlgorithm()),
                    KeyGeneratorFactory.getKeyGenerator(settings.getKeyGenerator()),
                    settings.getIterations()
            );

            byte[] encryptedPassword = encryptionService.encrypt(
                    settings.getMasterPassword().getBytes(),
                    settings.getMasterPassword());

            MetaData meta = new MetaData.Builder()
                    .nameDatabase(settings.getNameFile())
                    .encryptAlgorithm(settings.getEncryptAlgorithm())
                    .iterations(settings.getIterations())
                    .encryptedPasswordWithMeta(new String(encryptedPassword, StandardCharsets.UTF_8))
                    .keyGenerator(settings.getKeyGenerator())
                    .build();

            Database db = new Database.Builder()
                    .version(1)
                    .metaData(meta)
                    .build();

            String json = serializer.serialize(db);
            FileUtils.saveToFile(path, json);
            fileStorage.savePath(List.of(path.toString()));

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Database loadDatabase(String pathData) {
        Path path = Paths.get(pathData);
        String jsonContent = FileUtils.readFile(path);
        return serializer.deserialize(jsonContent, new TypeReference<>() {
        });
    }
}
