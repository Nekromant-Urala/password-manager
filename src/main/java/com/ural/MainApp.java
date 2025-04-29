package com.ural;

import com.ural.gui.windows.init.InitialWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final InitialWindow initWindow = new InitialWindow();

    @Override
    public void start(Stage stage) {
        initWindow.createWindow(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
