package com.ural.gui.windows.record;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.generator.GeneratorWindow;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecordHandler extends BaseHandlerEvent {
    private static final GeneratorWindow generatorWindow = new GeneratorWindow();

    @Override
    public void successfulEvent(Stage stage) {

    }

    void generationButton(Stage stage) {
        generatorWindow.createWindow(stage);
    }

    void checkGroup(ComboBox<String> comboBox, TextField field) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Другое".equals(newVal)) {
                field.setDisable(false); // Разблокируем при выборе "Другое"
                field.requestFocus(); // Фокусируемся на поле для ввода
            } else {
                field.setDisable(true); // Блокируем при выборе других вариантов
                field.clear(); // Очищаем поле
            }
        });
    }
}
