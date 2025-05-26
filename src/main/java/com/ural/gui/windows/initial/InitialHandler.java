package com.ural.gui.windows.initial;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.db.CreationFileWindow;
import com.ural.gui.windows.main.MainWindow;
import com.ural.manager.model.MasterPasswordHolder;
import com.ural.manager.service.AuthService;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;


public class InitialHandler extends BaseHandlerEvent {
    private final AuthService authService;

    public InitialHandler() {
        this.authService = new AuthService();
    }

    @Override
    public void successfulEvent(Stage stage) {
        // метод проверки пароля и переход на мейн окно
        char[] password = getPassword(stage);
        switch (authService.verifyPassword(password)) {
            case OK -> {
                MasterPasswordHolder.setMasterPassword(password);
                Arrays.fill(password, '\0');
                new MainWindow().createWindow(stage);
                stage.close();
            }
            case WRONG -> {
                Alert alert = showInfo("Ошибка ввода", "Введен неправильный пароль");
                alert.show();
            }
            case ERROR -> {
                Alert alert = showInfo("Ошибка загрузки", "База данных по такому пути не существует");
                alert.show();
                alert.setOnCloseRequest(e -> new CreationFileWindow().createWindow(stage));
            }
        }
    }

    private Alert showInfo(String tittle, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setWidth(335);
        return alert;
    }

    private char[] getPassword(Stage stage) {
        Parent root = stage.getScene().getRoot();
        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        TextField visiblePasswordField = (TextField) root.lookup("#visiblePasswordField");
        return passwordField.getText().isEmpty()
                ? visiblePasswordField.getText().toCharArray()
                : passwordField.getText().toCharArray();
    }

    @Override
    public void hideEvent(Stage stage) {
        Parent root = stage.getScene().getRoot();
        Button showHideButton = (Button) root.lookup("#showHideButton");
        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        TextField visiblePasswordField = (TextField) root.lookup("#visiblePasswordField");
        if (showHideButton.getText().equals("Показать")) {
            showVisiblePassword(showHideButton, visiblePasswordField, passwordField);
        } else {
            hidePassword(showHideButton, visiblePasswordField, passwordField);
        }
    }
}
