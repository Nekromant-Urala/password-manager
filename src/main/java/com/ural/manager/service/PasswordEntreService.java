package com.ural.manager.service;

import com.ural.manager.model.Database;
import com.ural.manager.model.PasswordEntre;
import com.ural.manager.serialization.JsonFileStorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PasswordEntreService {
    private final DatabaseService databaseService;
    private final Path pathFile;

    public PasswordEntreService() {
        this.databaseService = new DatabaseService();
        JsonFileStorage fileStorage = new JsonFileStorage();
        this.pathFile = Paths.get(fileStorage.loadPaths().get(0));
    }

    public void addPasswordEntre(PasswordEntre passwordEntre) {
        Database database = databaseService.loadDatabase(pathFile);
        database.getEntries().add(passwordEntre);
        databaseService.saveChanges(pathFile, database);
    }

    public void deletePasswordEntre(PasswordEntre passwordEntre){
        Database database = databaseService.loadDatabase(pathFile);
        database.getEntries().remove(passwordEntre);
        databaseService.saveChanges(pathFile, database);
    }


    public List<PasswordEntre> getAllElements() {
        Database database = databaseService.loadDatabase(pathFile);
        return database.getEntries();
    }
}
