package com.ural.gui.windows.record;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.generator.GeneratorWindow;
import com.ural.manager.model.PasswordEntre;
import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecordHandler extends BaseHandlerEvent {
    private static final GeneratorWindow generatorWindow = new GeneratorWindow();
    private static final String OTHER = "Другое";
    private PasswordEntre passwordEntre;

    @Override
    public void successfulEvent(Stage stage) {
        System.out.println(passwordEntre);
        // метод для внесения записи в базу
        // какой-то метод
        stage.close();
    }

    void generationEvent(Stage stage) {
        generatorWindow.createWindow(stage);
    }

    void setPasswordEntre(PasswordEntre.Builder passwordEntre) {
        this.passwordEntre = passwordEntre.build();
    }

    @Override
    public void checkPasswordField(Stage stage) {
        Parent root = stage.getScene().getRoot();
        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        TextField visiblePasswordField = (TextField) root.lookup("#visiblePasswordField");
        PasswordField passwordConfirmField = (PasswordField) root.lookup("#passwordConfirmField");
        TextField loginField = (TextField) root.lookup("#loginField");
        TextField tittleField = (TextField) root.lookup("#tittleField");
        Button okButton = (Button) root.lookup("#okButton");

        ChangeListener<String> listener = (obs, oldValue, newValue) -> {
            String password = passwordField.getText().isEmpty()
                    ? visiblePasswordField.getText()
                    : passwordField.getText();

            String confirmPassword = passwordConfirmField.getText();

            boolean validPassword = passwordConfirmField.isDisabled()
                    ? !password.isEmpty()
                    : password.equals(confirmPassword);

            boolean passwordNotEmpty = passwordConfirmField.isDisabled()  // Если поле подтверждения отключено
                    ? !password.isEmpty()
                    : (!password.isEmpty() && !passwordConfirmField.getText().isEmpty());

            boolean pathNotEmpty = !loginField.getText().isEmpty()
                    && !tittleField.getText().isEmpty();

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
        visiblePasswordField.textProperty().addListener(listener);
        passwordConfirmField.textProperty().addListener(listener);
        loginField.textProperty().addListener(listener);
        tittleField.textProperty().addListener(listener);

        // Добавляем слушатель на свойство disabled
        passwordConfirmField.disabledProperty().addListener((obs, oldVal, newVal) -> {
            listener.changed(null, null, null); // Принудительно вызываем проверку
        });
    }

    void checkGroup(Stage stage) {
        Parent root = stage.getScene().getRoot();
        ComboBox<String> comboBox = (ComboBox<String>) root.lookup("#comboBoxGroup");
        TextField field = (TextField) root.lookup("#groupField");

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (OTHER.equals(newVal)) {
                field.setDisable(false); // Разблокируем при выборе "Другое"
                field.requestFocus(); // Фокусируемся на поле для ввода
            } else {
                field.setDisable(true); // Блокируем при выборе других вариантов
                field.clear(); // Очищаем поле
            }
        });
    }
}
