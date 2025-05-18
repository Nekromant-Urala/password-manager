package com.ural.gui.windows.initial;


import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.main.MainWindow;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class InitialHandler extends BaseHandlerEvent {
    private static final MainWindow main = new MainWindow();

    public InitialHandler() {

    }

    @Override
    public void successfulEvent(Stage stage) {
        // метод проверки пароля и переход на мейн окно
        // validatePassword()
        main.createWindow(stage);
        stage.close();
    }

    public void checkPasswordField(Button okButton, PasswordField passwordField, TextField visiblePasswordField) {
        ChangeListener<String> listener = (obs, oldValue, newValue) -> {
            String password = passwordField.getText().isEmpty()
                    ? visiblePasswordField.getText()
                    : passwordField.getText();

            // Кнопка активна, если хотя бы одно поле не пустое
            okButton.setDisable(password.isEmpty());
        };

        // Добавляем слушатели к обоим полям
        passwordField.textProperty().addListener(listener);
        visiblePasswordField.textProperty().addListener(listener);

        // Инициализируем начальное состояние кнопки
        listener.changed(null, null, null);
    }
}
