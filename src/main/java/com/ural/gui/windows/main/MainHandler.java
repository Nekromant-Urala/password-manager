package com.ural.gui.windows.main;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.gui.windows.generator.GeneratorWindow;
import com.ural.gui.windows.record.RecordWindow;
import com.ural.manager.model.PasswordEntre;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.manager.serialization.JsonFileWatcher;
import com.ural.manager.service.PasswordEntreService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MainHandler extends BaseHandlerEvent {
    private final PasswordEntreService passwordEntreService;
    private JsonFileWatcher jsonFileWatcher;

    public MainHandler() {
        this.passwordEntreService = new PasswordEntreService();

    }

    void openGeneratorPasswordWindow(Stage stage) {
        new GeneratorWindow().createWindow(stage);
    }

    void openRecordWindow(Stage stage) {
        new RecordWindow().createWindow(stage);
    }

    void exitWindow(Stage stage) {
        jsonFileWatcher.stopWatching();
        stage.close();
        Platform.exit();
    }

    void filterByGroups(Stage stage, String nameGroup) {
        Parent root = stage.getScene().getRoot();
        Label allGroups = (Label) root.lookup("#allGroups");
        Label other = (Label) root.lookup("#other");
        TableView<PasswordEntre> table = (TableView<PasswordEntre>) root.lookup("#table");
        if (allGroups.getText().equals(nameGroup)) {
            table.setItems(loadPasswordEntries());
        } else if (other.getText().equals(nameGroup)) {
            table.setItems(FXCollections.observableArrayList(passwordEntreService.getOtherGroups()));
        } else {
            table.setItems(FXCollections.observableArrayList(passwordEntreService.getEntreOfGroups(nameGroup)));
        }
    }

    void getPassword(PasswordEntre passwordEntre) {
        passwordEntreService.getPasswordFromEntre(passwordEntre);
    }

    void deletePasswordEntre(PasswordEntre passwordEntre) {
        passwordEntreService.deletePasswordEntre(passwordEntre);
    }

    void startWatch(Stage stage) {
        Parent root = stage.getScene().getRoot();
        TableView<PasswordEntre> table = (TableView<PasswordEntre>) root.lookup("#table");
        table.setItems(loadPasswordEntries());
        jsonFileWatcher = new JsonFileWatcher(
                new JsonFileStorage().loadPaths().get(0),
                () -> table.setItems(loadPasswordEntries())
        );
        jsonFileWatcher.startWatching();
    }

    private ObservableList<PasswordEntre> loadPasswordEntries() {
        return FXCollections.observableArrayList(passwordEntreService.getAllElements());
    }
}
