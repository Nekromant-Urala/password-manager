package com.ural.gui.windows.initial;


import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.main.MainWindow;
import com.ural.manager.service.AuthService;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class InitialHandler extends BaseHandlerEvent {
//    private static final MainWindow main = new MainWindow();
    private final AuthService authService;

    public InitialHandler() {
        this.authService = new AuthService();
    }

    @Override
    public void successfulEvent(Stage stage) {
        // метод проверки пароля и переход на мейн окно
        if (authService.verifyPassword(getPassword(stage))) {
            new MainWindow().createWindow(stage);
            stage.close();
        } else {
            showInfo();
        }
    }

    private void showInfo() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText(null);
        alert.setContentText("Введен неправильный пароль");
        alert.setWidth(270);
        alert.setHeight(150);
        alert.show();
    }

    private String getPassword(Stage stage) {
        Parent root = stage.getScene().getRoot();
        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        TextField visiblePasswordField = (TextField) root.lookup("#visiblePasswordField");
        return passwordField.getText().isEmpty()
                ? visiblePasswordField.getText()
                : passwordField.getText();
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
