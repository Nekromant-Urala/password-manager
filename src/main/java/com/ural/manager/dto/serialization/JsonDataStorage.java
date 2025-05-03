package com.ural.manager.dto.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ural.manager.dto.data.Database;
import com.ural.manager.dto.data.MetaData;
import com.ural.manager.security.encryption.spec.AesGcmSpec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonDataStorage {
    // 1. метод для создания файла по указанному пути.
    // 2. сохранение данных, а именно паролей и конфига для мастера-паролей
    // 3. загрузка данных из файла, другими словами из json-файла
    // 4. сохранение данных при внесении изменений
    private final Path pathJsonFile;

    public JsonDataStorage(String path) {
        pathJsonFile = Paths.get(path);
        createJson(pathJsonFile);
    }

    /**
     * Метод для создания файла, который будет хранить данные пользователя по указанному пути.
     *
     * @param jsonFile путь, по которому необходимо создать файл
     */
    public void createJson(Path jsonFile) {
        if (!Files.exists(jsonFile)) {
            try {
                Files.createFile(jsonFile.toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Такой файл уже существует!");
            }
        }
    }

//    public void createBootConfig(String salt, String iv) {
//        Database db = new Database();
//        db.setVersion(1);
//
//        MetaData meta = new MetaData();
//        meta.setEncryption(AesGcmSpec.AES_MODE.getSpec());
//        meta.setKeyDerivation(AesGcmSpec.PBKDF2WithHmacSHA256.getSpec());
//        meta.setIterations(AesGcmSpec.ITERATION_COUNT.getNumber());
//        meta.setSalt(salt);
//        meta.setIv(iv);
//
//        db.setMetaData(meta);
//        db.setEntries(new ArrayList<>());
//        saveToJson(db);
//    }

    private void saveToJson(Database db) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String jsonResult = objectMapper.writeValueAsString(db);
            Files.writeString(pathJsonFile, jsonResult);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка сериализации!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getPathJsonFile() {
        return pathJsonFile;
    }
}
