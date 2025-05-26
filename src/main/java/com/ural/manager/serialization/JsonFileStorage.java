package com.ural.manager.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ural.manager.service.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonFileStorage {
    private final JsonSerializer serializer;
    private static final Path CONFIG_FILE = Paths.get("app_config.json");

    public JsonFileStorage() {
        this.serializer = new JsonSerializer();
    }

    public void savePath(List<String> pathsList) {
        try {
            FileUtils.createFileIfNotExists(CONFIG_FILE);
            String paths = serializer.serialize(pathsList);
            FileUtils.saveToFile(CONFIG_FILE, paths);
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом:" + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Ошибка сериализации объекта.");
        }
    }

    public Path getAbsolutePath(String folder, String file) {
        Path pathFolder = Paths.get(folder);
        return pathFolder.resolve(file + ".json");
    }

    public List<String> loadPaths() {
        try {
            // 1. Проверяем существование файла и создаём его при необходимости
            if (!Files.exists(CONFIG_FILE)) {
                FileUtils.createFileIfNotExists(CONFIG_FILE);
                savePath(List.of());
                return List.of();
            }

            // 2. Читаем содержимое файла
            String jsonValue = FileUtils.readFile(CONFIG_FILE);

            // 3. Если файл пустой, возвращаем пустой список
            if (jsonValue == null || jsonValue.trim().isEmpty()) {
                return List.of();
            }

            // 4. Пытаемся десериализовать JSON
            return serializer.deserialize(jsonValue, new TypeReference<>() {
            });

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
            return List.of();
        } catch (RuntimeException e) {
            System.err.println("Ошибка при парсинге JSON: " + e.getMessage());
            return List.of();
        }
    }
}
