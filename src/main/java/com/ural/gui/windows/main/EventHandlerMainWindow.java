package com.ural.gui.windows.main;

import com.ural.gui.windows.EventHandler;
import com.ural.gui.windows.generator.PasswordGeneratorWindow;
import com.ural.gui.windows.record.RecordWindow;
import javafx.stage.Stage;

public class EventHandlerMainWindow implements EventHandler {
    private static final PasswordGeneratorWindow generatorWindow = new PasswordGeneratorWindow();
    private static final RecordWindow recordWindow = new RecordWindow();

    void openGeneratorPasswordWindow(Stage owner) {
        generatorWindow.createWindow(owner);
    }

    void openRecordWindow(Stage owner) {
        recordWindow.createWindow(owner);
    }

    void exitWindow(Stage owner) {
        owner.close();
    }

}
