package com.ural.gui.windows.main;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.db.CreationFileWindow;
import com.ural.gui.windows.generator.GeneratorWindow;
import com.ural.gui.windows.record.RecordWindow;
import javafx.stage.Stage;

public class MainHandler extends BaseHandlerEvent {
    private static final GeneratorWindow generatorWindow = new GeneratorWindow();
    private static final RecordWindow recordWindow = new RecordWindow();
    private static final CreationFileWindow createWindow = new CreationFileWindow();


    void openGeneratorPasswordWindow(Stage stage) {
        generatorWindow.createWindow(stage);
    }

    void openRecordWindow(Stage stage) {
        recordWindow.createWindow(stage);
    }

    void exitWindow(Stage stage) {
        stage.close();
    }

    void openDatabaseWindow(Stage stage) {
        createWindow.createWindow(stage);
    }
}
