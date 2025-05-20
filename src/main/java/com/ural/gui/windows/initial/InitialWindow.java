package com.ural.gui.windows.initial;

import com.ural.gui.core.Window;
import com.ural.manager.serialization.JsonFileStorage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InitialWindow implements Window {
    private static final String NOT_FILE_FOUND = "Ни одного файла не найдено";
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 210;
    private static final int TEXT_FIELD_WIDTH = 190;
    private static final int BUTTON_WIDTH = 70;
    private static final Insets MARGINS_ROOT = new Insets(0, 15, 0, 15);
    private static final Insets MARGINS_ELEMENTS = new Insets(10, 0, 0, 0);
    private static final Insets MARGINS_HALF = new Insets(5, 0, 0, 0);
    private static final int SPACING = 10;

    private final InitialHandler handler;
    private final JsonFileStorage fileStorage;

    public InitialWindow() {
        this.handler = new InitialHandler();
        this.fileStorage = new JsonFileStorage();
    }

    @Override
    public void createWindow(Stage stage) {
        Stage initialStage = new Stage();
        // основные поля окна
        Button okButton = createButton("OK");
        okButton.setId("okButton");
        okButton.setDisable(true);
        Button exitButton = createButton("Отмена");
        exitButton.setId("exitButton");
        Button showHideButton = createButton("Показать");
        showHideButton.setId("showHideButton");

        PasswordField passwordField = createPasswordField();
        passwordField.setId("passwordField");
        TextField visiblePasswordField = createVisiblePasswordField();
        visiblePasswordField.setId("visiblePasswordField");

        Label masterPassLabel = createLabel();

        // Главный контейнер
        VBox root = new VBox();
        root.setPadding(MARGINS_ROOT);
        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().addAll(
                createHader(),
                createPathFileLabel(),
                createSeparator(),
                masterPassLabel,
                createPasswordContainer(passwordField, visiblePasswordField, showHideButton),
                createSeparator(),
                createButtonContainer(okButton, exitButton)
        );

        // Настройка окна
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        initialStage.setTitle("Ввод мастер-пароля");
        initialStage.setScene(scene);
        initialStage.setResizable(false);
        // установка привязок
        setupHandler(okButton, exitButton, showHideButton, initialStage);
        initialStage.show();
    }

    public static void createInit(Stage stage) {
        new InitialWindow().createWindow(stage);
    }

    private Label createLabel() {
        Label label = new Label("Мастер-пароль:");
        label.setPadding(MARGINS_ELEMENTS);
        return label;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(MARGINS_ELEMENTS);
        return separator;
    }

    private Label createPathFileLabel() {
        Label label = new Label(NOT_FILE_FOUND);
        String pathFile = fileStorage.loadPaths().get(0);
        label.setText(pathFile);
        label.setTextFill(Color.GRAY);
        label.setPadding(MARGINS_HALF);
        return label;
    }

    private Label createHader() {
        Label label = new Label("Введите мастер-пароль");
        label.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
        label.setPadding(MARGINS_ELEMENTS);
        return label;
    }

    private TextField createVisiblePasswordField() {
        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Введите мастер-пароль");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setPrefWidth(TEXT_FIELD_WIDTH);
        visiblePasswordField.setId("visiblePasswordField");
        return visiblePasswordField;
    }

    private PasswordField createPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите мастер-пароль");
        passwordField.setPrefWidth(TEXT_FIELD_WIDTH);
        passwordField.setId("passwordField");
        return passwordField;
    }

    private HBox createPasswordContainer(PasswordField passwordField, TextField visiblePasswordField, Button showHideButton) {
        HBox passwordContainer = new HBox();
        passwordContainer.setSpacing(SPACING);
        passwordContainer.setPadding(MARGINS_ELEMENTS);
        passwordContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        HBox.setHgrow(visiblePasswordField, Priority.ALWAYS);
        passwordContainer.getChildren().addAll(passwordField, visiblePasswordField, showHideButton);
        return passwordContainer;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(BUTTON_WIDTH);
        return button;
    }

    private HBox createButtonContainer(Button okButton, Button exitButton) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_RIGHT);
        container.setSpacing(SPACING);
        container.setPadding(MARGINS_ELEMENTS);
        container.getChildren().addAll(okButton, exitButton);
        return container;
    }

    private void setupHandler(Button okButton, Button exitButton, Button showHideButton, Stage stage) {
        handler.checkPasswordField(stage);

        showHideButton.setOnAction(event -> {
            handler.hideEvent(stage);
        });

        okButton.setOnAction(event -> {
            handler.successfulEvent(stage);
        });

        exitButton.setOnAction(event -> {
            handler.closingEvent(stage);
        });
    }
}
