package com.ural.gui.windows.db;

import com.ural.gui.windows.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    void browseButtonHandler(Stage owner, TextField keyFileField) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите ключевую директорию");

        File path = directoryChooser.showDialog(owner);

        if (path != null) {
            keyFileField.setText(path.getAbsolutePath());
        }
    }

    void okButtonHandler(Stage owner, Button button) {

        System.out.println("Урааа");
        owner.close();
    }

    void checkPasswordField(PasswordField passwordField, TextField visiblePasswordField,  PasswordField passwordConfirmField, TextField nameFileField, TextField pathFileField) {
        ChangeListener<String> listener = (obs, oldValue, newValue) -> {
//            boolean
        };
        passwordField.textProperty().addListener(listener);
        visiblePasswordField.textProperty().addListener(listener);
        passwordConfirmField.textProperty().addListener(listener);
        nameFileField.textProperty().addListener(listener);
        pathFileField.textProperty().addListener(listener);
    }

    /**
     * Метод для обработки события выхода из окна инициализации.
     *
     * @param owner
     */
    void cancelButtonHandler(Stage owner) {
        owner.close();
    }

    void showHideButtonHandler(Button showHideButton, PasswordField masterPassField, TextField visiblePasswordField, PasswordField passwordConfirmField) {
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

}
