package com.ural.gui.windows.generator;

import com.ural.gui.windows.EventHandler;
import javafx.stage.Stage;

public class EventHandlerGeneratorWindow implements EventHandler {

    void exitButtonHandler(Stage owner) {
        owner.close();
    }
}
