package com.ural.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ural.manager.model.Database;
import com.ural.manager.model.MasterPasswordHolder;
import com.ural.manager.model.MetaData;
import com.ural.manager.model.SettingsData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.manager.serialization.JsonSerializer;
import com.ural.manager.serialization.Serializer;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.KeyGeneratorFactory;
import com.ural.security.encryption.service.EncryptionService;
import org.bouncycastle.openssl.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
                    settings.getMasterPassword().toCharArray());

            MetaData meta = new MetaData.Builder()
                    .nameDatabase(settings.getNameFile())
                    .encryptAlgorithm(settings.getEncryptAlgorithm())
                    .iterations(settings.getIterations())
                    .encryptedPasswordWithMeta(new String(encryptedPassword, StandardCharsets.UTF_8))
                    .keyGenerator(settings.getKeyGenerator())
                    .description(settings.getDescription())
                    .build();

            Database db = new Database.Builder()
                    .addVersion(1)
                    .addMetaData(meta)
                    .build();

            String json = serializer.serialize(db);
            byte[] encryptedJson = EncryptionService.encryptDefault(json.getBytes(StandardCharsets.UTF_8), settings.getMasterPassword().toCharArray());
            FileUtils.saveToFile(path, new String(encryptedJson, StandardCharsets.UTF_8));
            fileStorage.savePath(List.of(path.toString()));
        } catch (IOException e) {
            System.err.println("Ошибка сериализации базы данных. " + e.getMessage());
        } catch (KeyException | InvalidKeySpecException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.err.println("Ошибка при шифровании. " + e.getMessage());
        }
    }

    public void saveChanges(Path path, Database database) {
        try {
            String jsonFormat = serializer.serialize(database);
            byte[] encryptedJson = EncryptionService.encryptDefault(jsonFormat.getBytes(StandardCharsets.UTF_8), MasterPasswordHolder.getMasterPassword());
            FileUtils.saveToFile(path, new String(encryptedJson, StandardCharsets.UTF_8));
        } catch (JsonProcessingException | EncryptionException | InvalidKeySpecException | NoSuchAlgorithmException |
                 KeyException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException e) {
            System.err.println("Ошибка при сохранении изменений в базе данных. " + e.getMessage());
        }
    }

    public Database loadDatabase(Path pathData) throws JsonProcessingException, FileNotFoundException {
        byte[] jsonContent = new byte[0];
        try {
            String encryptedJsonContent = FileUtils.readFile(pathData);
            jsonContent = EncryptionService.decryptDefault(encryptedJsonContent.getBytes(StandardCharsets.UTF_8), MasterPasswordHolder.getMasterPassword());
        } catch (KeyException | InvalidKeySpecException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | IOException e) {
            System.err.println("Ошибка при дешифровании. " + e.getMessage());
            if (!Files.exists(pathData)) {
                throw new FileNotFoundException("Файл не был найден!");
            }
        }
        return serializer.deserialize(new String(jsonContent), new TypeReference<>() {
        });
    }
}
