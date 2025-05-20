package com.ural.gui.core;

import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class BaseHandlerEvent {
    public void searchEvent(Stage stage, TextField pathFileField) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выбор папки для размещения файла");

        File selectedFile = directoryChooser.showDialog(stage);
        if (selectedFile != null) {
            pathFileField.setText(selectedFile.getAbsolutePath());
        }
    }
    public void closingEvent(Stage stage) {
        stage.close();
    }

    public void successfulEvent(Stage stage) {
        stage.close();
    }

    public void hideEvent(Button showHideButton, PasswordField passwordField, TextField visiblePasswordField,PasswordField passwordConfirmField) {
        if (showHideButton.getText().equals("Показать")) {
            showVisiblePassword(showHideButton, visiblePasswordField, passwordField);
            passwordConfirmField.setDisable(true);
        } else {
            hidePassword(showHideButton, visiblePasswordField, passwordField);
            passwordConfirmField.setDisable(false);
        }
    }

    public void hideEvent(Button showHideButton, PasswordField passwordField, TextField visiblePasswordField) {
        if (showHideButton.getText().equals("Показать")) {
            showVisiblePassword(showHideButton, visiblePasswordField, passwordField);
        } else {
            hidePassword(showHideButton, visiblePasswordField, passwordField);
        }
    }

    private void showVisiblePassword(Button showHideButton, TextField visiblePasswordField, PasswordField passwordField) {
        visiblePasswordField.setText(passwordField.getText());
        visiblePasswordField.setVisible(true);
        visiblePasswordField.setManaged(true);
        passwordField.setVisible(false);
        passwordField.setManaged(false);
        passwordField.clear();
        showHideButton.setText("Скрыть");
    }

    private void hidePassword(Button showHideButton, TextField visiblePasswordField, PasswordField passwordField) {
        passwordField.setText(visiblePasswordField.getText());
        passwordField.setVisible(true);
        passwordField.setManaged(true);
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.clear();
        showHideButton.setText("Показать");
    }

    public Spinner<Integer> createSpinner(int initialNum, int defaultNum, int step, int widthSpinner) {

        Spinner<Integer> spinner = new Spinner<>(initialNum, Integer.MAX_VALUE, defaultNum);
        spinner.setPrefWidth(widthSpinner);

        // Установка фабрики значений с правильной логикой инкремента/декремента
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();

        // Обработчик для текстового поля (если пользователь вводит значение вручную)
        spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                // Парсим введенное значение
                int value = Integer.parseInt(newValue);
                // Проверяем границы
                if (value < factory.getMin()) {
                    value = factory.getMin();
                } else if (value > factory.getMax()) {
                    value = factory.getMax();
                }
                factory.setValue(value);
            } catch (NumberFormatException e) {
                // Если введено не число, восстанавливаем предыдущее значение
                if (!newValue.matches("\\d*")) {
                    spinner.getEditor().setText(oldValue);
                }
            }
        });
        factory.setAmountToStepBy(step);
        spinner.setEditable(true);
        return spinner;
    }
}
