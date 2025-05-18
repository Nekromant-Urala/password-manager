package com.ural.gui.windows.record;

import com.ural.gui.core.BaseHandlerEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecordHandler extends BaseHandlerEvent {

    @Override
    public void successfulEvent(Stage stage) {

    }

    void generationButton(Stage owner) {
//        generatorWindow.createWindow(owner);
    }

    void showHideButton(Button showHideButton, PasswordField masterPassField, TextField visiblePasswordField, PasswordField passwordConfirmField) {
        if (showHideButton.getText().equals("Показать")) {
            // Показываем пароль
            visiblePasswordField.setText(masterPassField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordConfirmField.setDisable(true);
            masterPassField.setVisible(false);
            masterPassField.setManaged(false);
            showHideButton.setText("Скрыть");
        } else {
            // Скрываем пароль
            masterPassField.setText(visiblePasswordField.getText());
            masterPassField.setVisible(true);
            masterPassField.setManaged(true);
            passwordConfirmField.setDisable(false);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            showHideButton.setText("Показать");
        }
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
