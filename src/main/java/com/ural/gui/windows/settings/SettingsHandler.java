package com.ural.gui.windows.settings;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.manager.model.Database;
import com.ural.manager.model.MetaData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.manager.service.DatabaseService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsHandler extends BaseHandlerEvent {
    private final DatabaseService databaseService;
    private final JsonFileStorage fileStorage;
    private final MetaData metaData;

    public SettingsHandler() {
        this.databaseService = new DatabaseService();
        this.fileStorage = new JsonFileStorage();
        this.metaData = loadMetaData();
    }

    private MetaData loadMetaData() {
        Path path = Paths.get(fileStorage.loadPaths().get(0));
        Database database;
        try {
            database = databaseService.loadDatabase(path);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке базы данных. " + e.getMessage());
            return null;
        }
        return database.getMetaData();
    }

    public String getDescriptionDatabase() {
        String description = metaData.getDescription();
        if (description == null) {
            return "Описание файла отсутствует";
        }
        return description;
    }

    public String getPathFile() {
        return fileStorage.loadPaths().get(0);
    }

    public String getEncryptAlgorithm() {
        return metaData.getEncryptAlgorithm();
    }

    public String getKeyGenerator() {
        return metaData.getKeyGenerator();
    }

}
