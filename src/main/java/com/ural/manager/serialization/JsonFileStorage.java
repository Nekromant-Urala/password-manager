package com.ural.manager.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ural.manager.service.FileUtils;

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
        FileUtils.createFileIfNotExists(CONFIG_FILE);
        String paths = serializer.serialize(pathsList);
        FileUtils.saveToFile(CONFIG_FILE, paths);
    }

    public Path getAbsolutePath(String folder, String file) {
        Path pathFolder = Paths.get(folder);
        return pathFolder.resolve(file + ".json");
    }

    public List<String> loadPaths() {
        if (!Files.exists(CONFIG_FILE)) {
            FileUtils.createFileIfNotExists(CONFIG_FILE);
            savePath(List.of());
        }
        String jsonValue = FileUtils.readFile(CONFIG_FILE);
        if (!jsonValue.isEmpty()) {
            return serializer.deserialize(jsonValue, new TypeReference<>() {});
        }
        return List.of();
    }
}
