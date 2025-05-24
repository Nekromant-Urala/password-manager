package com.ural.gui.core;

import javafx.scene.control.Tab;
import javafx.stage.Stage;

@FunctionalInterface
public interface TabView {
    Tab createTab(Stage stage);
}
