package com.ural.gui.windows.record;

import com.ural.gui.windows.EventHandler;
import com.ural.gui.windows.generator.PasswordGeneratorWindow;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EventHandlerRecordWindow implements EventHandler {
    private static final PasswordGeneratorWindow generatorWindow = new PasswordGeneratorWindow();

    void exitWindow(Stage owner) {
        owner.close();
    }

    void addRecordButton(Stage owner) {
    }

    void generationButton(Stage owner) {
        generatorWindow.createWindow(owner);
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

    void checkPasswordField(PasswordField passwordField, TextField visiblePasswordField, PasswordField passwordConfirmField, TextField nameFileField, TextField pathFileField, Button button) {
        ChangeListener<String> listener = (obs, oldValue, newValue) -> {
            String password = passwordField.getText().isEmpty()
                    ? visiblePasswordField.getText()
                    : passwordField.getText();

            String confirmPassword = passwordConfirmField.getText();

            boolean validPassword = password.equals(confirmPassword);
            boolean passwordNotEmpty = !passwordField.getText().isEmpty() &&
                    !passwordConfirmField.getText().isEmpty();
            boolean pathNotEmpty = !nameFileField.getText().isEmpty() &&
                    !pathFileField.getText().isEmpty();


            if (!validPassword && passwordNotEmpty) {
                passwordConfirmField.setStyle("-fx-background-color: #FF8080;-fx-background-radius: 3px;");
                passwordField.setStyle("-fx-background-color: #FF8080;-fx-background-radius: 3px;");
            } else {
                passwordField.setStyle("");
                passwordConfirmField.setStyle("");
            }

            button.setDisable(!pathNotEmpty || !validPassword || !passwordNotEmpty);
        };
        passwordField.textProperty().addListener(listener);
        passwordConfirmField.textProperty().addListener(listener);
        nameFileField.textProperty().addListener(listener);
        pathFileField.textProperty().addListener(listener);
    }
}
