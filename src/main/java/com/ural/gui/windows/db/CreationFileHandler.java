package com.ural.gui.windows.db;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.initial.InitialWindow;
import com.ural.manager.model.SettingsData;
import com.ural.manager.service.DatabaseService;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreationFileHandler extends BaseHandlerEvent {
    private final DatabaseService databaseService;
    private Button okButton;

    public CreationFileHandler() {
        databaseService = new DatabaseService();
    }

    public void getBasicField(Button okButton) {
        this.okButton = okButton;
    }

    public void successfulEvent(Stage stage, SettingsData.Builder settings) {
        SettingsData set = settings.build();
        System.out.println(set);
        databaseService.createDatabase(set);
        stage.close();
        InitialWindow.createInit(new Stage());
    }

    public void checkPasswordField(PasswordField passwordField, TextField visiblePasswordField, PasswordField passwordConfirmField, TextField nameFileField, TextField pathFileField) {
        ChangeListener<String> listener = (obs, oldValue, newValue) -> {
            String password = passwordField.getText().isEmpty()
                    ? visiblePasswordField.getText()
                    : passwordField.getText();

            String confirmPassword = passwordConfirmField.getText();

            boolean validPassword = passwordConfirmField.isDisabled()  // Если поле подтверждения отключено
                    || password.equals(confirmPassword);              // Или пароли совпадают

            boolean passwordNotEmpty = passwordConfirmField.isDisabled()  // Если поле подтверждения отключено
                    || (!passwordField.getText().isEmpty()               // Или оба поля заполнены
                    && !passwordConfirmField.getText().isEmpty());

            boolean pathNotEmpty = !nameFileField.getText().isEmpty()
                    && !pathFileField.getText().isEmpty();

            if (!passwordConfirmField.isDisabled() && !validPassword && passwordNotEmpty) {
                passwordConfirmField.setStyle("-fx-background-color: #FF8080; -fx-background-radius: 3px;");
                passwordField.setStyle("-fx-background-color: #FF8080; -fx-background-radius: 3px;");
            } else {
                passwordField.setStyle("");
                passwordConfirmField.setStyle("");
            }

            okButton.setDisable(!pathNotEmpty || !validPassword || !passwordNotEmpty);
        };

        // Добавляем слушатели
        passwordField.textProperty().addListener(listener);
        passwordConfirmField.textProperty().addListener(listener);
        nameFileField.textProperty().addListener(listener);
        pathFileField.textProperty().addListener(listener);

        // Добавляем слушатель на свойство disabled
        passwordConfirmField.disabledProperty().addListener((obs, oldVal, newVal) -> {
            listener.changed(null, null, null); // Принудительно вызываем проверку
        });
    }
}
