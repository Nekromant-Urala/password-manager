package com.ural.gui.windows.chang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ural.gui.core.BaseHandlerEvent;
import com.ural.manager.model.Database;
import com.ural.manager.model.MasterPasswordHolder;
import com.ural.manager.model.MetaData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.manager.service.DatabaseService;
import com.ural.manager.service.MetaDataService;
import com.ural.manager.service.PasswordEntreService;
import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ChangPasswordHandler extends BaseHandlerEvent {
    private final MetaDataService metaDataService;
    private final PasswordEntreService passwordEntreService;
    private final DatabaseService databaseService;
    private final Path pathFile;

    public ChangPasswordHandler() {
        this.metaDataService = new MetaDataService();
        this.passwordEntreService = new PasswordEntreService();
        this.databaseService = new DatabaseService();
        JsonFileStorage fileStorage = new JsonFileStorage();
        pathFile = Paths.get(fileStorage.loadPaths().get(0));
    }

    @Override
    public void successfulEvent(Stage stage) {
        char[] password = getPassword(stage);

        MetaData meta;
        if ((meta = metaDataService.changPassword(new String(password))) != null) {

            Database database1 = new Database.Builder()
                    .addVersion(1)
                    .addMetaData(meta)
                    .addEntries(passwordEntreService.updateKeyForEntries(password))
                    .build();

            MasterPasswordHolder.clear();
            MasterPasswordHolder.setMasterPassword(password);

            databaseService.saveChanges(pathFile, database1);

            Arrays.fill(password, '\0');
            stage.close();
        } else {
            stage.close();
            Alert alert = showInfo("Ошибка смены пароля", "Не удалось сменить пароль. Пароль остался прежним.");
            alert.show();
        }
        Alert alert = showInfo("Смена пароля.", "Пароль успешно сменен!");
        alert.show();
    }

    private Alert showInfo(String tittle, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setWidth(335);
        return alert;
    }

    @Override
    public void checkPasswordField(Stage stage) {
        Parent root = stage.getScene().getRoot();
        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        TextField visiblePasswordField = (TextField) root.lookup("#visiblePasswordField");
        PasswordField passwordConfirmField = (PasswordField) root.lookup("#passwordConfirmField");
        Button okButton = (Button) root.lookup("#okButton");

        ChangeListener<String> listener = (obs, oldValue, newValue) -> {
            String password = passwordField.getText().isEmpty()
                    ? visiblePasswordField.getText()
                    : passwordField.getText();

            String confirmPassword = passwordConfirmField.getText();

            boolean validPassword = passwordConfirmField.isDisabled()  // Если поле подтверждения отключено
                    ? !password.isEmpty()
                    : password.equals(confirmPassword);            // Или пароли совпадают

            boolean passwordNotEmpty = passwordConfirmField.isDisabled()  // Если поле подтверждения отключено
                    ? !password.isEmpty()
                    : (!password.isEmpty() && !passwordConfirmField.getText().isEmpty());


            if (!passwordConfirmField.isDisabled() && !validPassword && passwordNotEmpty) {
                passwordConfirmField.setStyle("-fx-background-color: #FF8080; -fx-background-radius: 3px;");
                passwordField.setStyle("-fx-background-color: #FF8080; -fx-background-radius: 3px;");
            } else {
                passwordField.setStyle("");
                passwordConfirmField.setStyle("");
            }

            okButton.setDisable(!validPassword || !passwordNotEmpty);
        };
        // Добавляем слушатели
        passwordField.textProperty().addListener(listener);
        visiblePasswordField.textProperty().addListener(listener);
        passwordConfirmField.textProperty().addListener(listener);

        // Добавляем слушатель на свойство disabled
        passwordConfirmField.disabledProperty().addListener((obs, oldVal, newVal) -> {
            listener.changed(null, null, null); // Принудительно вызываем проверку
        });
    }
}
