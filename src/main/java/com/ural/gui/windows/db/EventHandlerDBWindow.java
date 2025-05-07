package com.ural.gui.windows.db;

import com.ural.gui.windows.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Класс для обработки событий при нажатии на кнопки в окне создания базы данных
 */
public class EventHandlerDBWindow implements EventHandler {

    /**
     * Метод для обработки события кнопки поиска директории для хранения файла
     *
     * @param owner
     * @param keyFileField
     */
    void browseButton(Stage owner, TextField keyFileField) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите ключевую директорию");

        File path = directoryChooser.showDialog(owner);

        if (path != null) {
            keyFileField.setText(path.getAbsolutePath());
        }
    }

    void okButton(Stage owner, Button button) {

        System.out.println("Урааа");
        owner.close();
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

    /**
     * Метод для обработки события выхода из окна инициализации.
     *
     * @param owner
     */
    void cancelButton(Stage owner) {
        owner.close();
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

    Spinner<Integer> getIntegerSpinner() {
        Spinner<Integer> spinner = new Spinner<>(1, Integer.MAX_VALUE, 600_000); // min, max, initial
        spinner.setPrefWidth(150);

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

        // Настройка инкремента/декремента на основе текущего значения
        factory.setAmountToStepBy(1_000); // Шаг изменения

        spinner.setEditable(true);
        return spinner;
    }
}
