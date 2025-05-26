package com.ural.app;

import com.ural.gui.windows.db.CreationFileWindow;
import com.ural.gui.windows.initial.InitialWindow;
import com.ural.manager.serialization.JsonFileStorage;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class MainApp extends Application {
    private final InitialWindow initialWindow;
    private final CreationFileWindow creationWindow;
    private final JsonFileStorage jsonFileStorage;

    public MainApp() {
        initialWindow = new InitialWindow();
        creationWindow = new CreationFileWindow();
        jsonFileStorage = new JsonFileStorage();
    }

    @Override
    public void start(Stage stage) {
        List<String> pathsFile = jsonFileStorage.loadPaths();
        if (!pathsFile.isEmpty()) {
            initialWindow.createWindow(stage);
        } else {
            creationWindow.createWindow(stage);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
