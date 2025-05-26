package com.ural.gui.windows.db.tabs;

import com.ural.gui.core.TabView;
import com.ural.manager.model.SettingsData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BasicTabView implements TabView {
    private static final int TEXT_FIELD_WIDTH = 300;
    private static final String TEXT_IN_PASSWORD_FILED = "Введите мастер-пароль";
    private static final Insets MARGINS_CONTAINER = new Insets(5, 0, 0, 15);
    private static final Insets MARGINS_LABEL = new Insets(10, 0, 0, 15);
    private static final int SPACING_CONTAINER = 10;
    private static final int BUTTON_WIDTH = 70;

    private final SettingsData.Builder settings;

    public BasicTabView(SettingsData.Builder settings) {
        this.settings = settings;
    }

    @Override
    public Tab createTab(Stage stage) {
        // Основные поля вкладки
        PasswordField passwordField = createPasswordField();
        passwordField.setId("passwordField");
        TextField visiblePasswordField = createVisiblePasswordField();
        visiblePasswordField.setId("visiblePasswordField");
        PasswordField passwordConfirmField = createPasswordField();
        passwordConfirmField.setId("passwordConfirmField");
        TextField nameFileField = createTextField("Название файла");
        nameFileField.setId("nameFileField");
        TextField pathFileField = createTextField("Путь по которому будет храниться файл");
        pathFileField.setId("pathFileField");

        Button showHideButton = createButton("Показать");
        showHideButton.setId("showHideButton");
        Button browseButton = createButton("Обзор...");
        browseButton.setId("browseButton");

        // установка привязок
        setupBindings(passwordField, visiblePasswordField, nameFileField, pathFileField);

        // Главный контейнер основной вкладки
        VBox content = new VBox();

        // Описание для базы данных
        content.getChildren().addAll(
                createLabel("Введите мастер-пароль:"),
                createPasswordContainer(passwordField, visiblePasswordField, showHideButton),
                createLabel("Подтвердите мастер-пароль:"),
                createConfirmPasswordContainer(passwordConfirmField),
                createLabel("Введите имя базы данных:"),
                createNameFileContainer(nameFileField),
                createLabel("Создания файла по указанному пути:"),
                createPathFileContainer(pathFileField, browseButton)
        );

        Tab basicTab = new Tab("Основные");
        basicTab.setClosable(false);
        basicTab.setContent(content);
        return basicTab;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setPadding(MARGINS_LABEL);
        return label;
    }

    private HBox createConfirmPasswordContainer(PasswordField passwordConfirmField) {
        HBox confirmPasswordContainer = new HBox();
        confirmPasswordContainer.setPadding(MARGINS_CONTAINER);
        confirmPasswordContainer.setAlignment(Pos.CENTER_LEFT);
        confirmPasswordContainer.getChildren().addAll(passwordConfirmField);
        return confirmPasswordContainer;
    }

    private TextField createVisiblePasswordField() {
        TextField visibleField = new TextField();
        visibleField.setPromptText(TEXT_IN_PASSWORD_FILED);
        visibleField.setVisible(false);
        visibleField.setManaged(false);
        visibleField.setPrefWidth(TEXT_FIELD_WIDTH);
        return visibleField;
    }

    private PasswordField createPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(TEXT_IN_PASSWORD_FILED);
        passwordField.setPrefWidth(TEXT_FIELD_WIDTH);
        return passwordField;
    }

    private HBox createPasswordContainer(PasswordField passwordField, TextField visiblePasswordField, Button showHideButton) {
        HBox passwordContainer = new HBox();
        passwordContainer.setPadding(MARGINS_CONTAINER);
        passwordContainer.setSpacing(SPACING_CONTAINER);
        passwordContainer.setAlignment(Pos.CENTER_LEFT);
        passwordContainer.getChildren().addAll(passwordField, visiblePasswordField, showHideButton);

        return passwordContainer;
    }

    private HBox createNameFileContainer(TextField nameField) {
        HBox container = new HBox();
        container.setPadding(MARGINS_CONTAINER);
        container.setSpacing(SPACING_CONTAINER);
        container.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().addAll(nameField);
        return container;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(BUTTON_WIDTH);
        return button;
    }

    private TextField createTextField(String text) {
        TextField textField = new TextField();
        textField.setPromptText(text);
        textField.setPrefWidth(TEXT_FIELD_WIDTH);
        return textField;
    }

    private HBox createPathFileContainer(TextField pathField, Button browseButton) {
        HBox container = new HBox();
        container.setPadding(MARGINS_CONTAINER);
        container.setSpacing(SPACING_CONTAINER);
        container.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().addAll(pathField, browseButton);
        return container;
    }

    // установка привязок
    private void setupBindings(PasswordField passwordField, TextField visiblePassword, TextField nameFileField, TextField pathFileField) {
        passwordField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (passwordField.isVisible()) {
                settings.masterPassword(newValue);
            }
        });

        visiblePassword.textProperty().addListener((obs, oldValue, newValue) -> {
            if (visiblePassword.isVisible()) {
                settings.masterPassword(newValue);
            }
        });

        nameFileField.textProperty().addListener((obs, oldValue, newValue) -> {
            settings.nameFile(newValue);
        });

        pathFileField.textProperty().addListener((obs, oldValue, newValue) -> {
            settings.pathFile(newValue);
        });
    }
}
