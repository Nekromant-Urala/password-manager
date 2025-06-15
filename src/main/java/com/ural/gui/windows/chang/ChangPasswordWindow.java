package com.ural.gui.windows.chang;

import com.ural.gui.core.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangPasswordWindow implements Window {
    private final ChangPasswordHandler handler;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 130;

    private static final Insets ROOT_ALIGNMENT = new Insets(10, 15, 10, 15);
    private static final int ROOT_SPACING = 10;

    public ChangPasswordWindow() {
        this.handler = new ChangPasswordHandler();
    }

    @Override
    public void createWindow(Stage stage) {
        Stage changPasswordStage = new Stage();
        changPasswordStage.setTitle("Изменение мастер-пароля");

        // блокировка окна, на котором вызывается данное окно
        changPasswordStage.initModality(Modality.WINDOW_MODAL);
        changPasswordStage.initOwner(stage);

        Button okButton = new Button("Сменить");
        okButton.setId("okButton");
        Button exitButton = new Button("Закрыть");
        exitButton.setId("exitButton");

        Label passwordLabel = createLabel("Пароль:");
        PasswordField passwordField = createPasswordField();
        passwordField.setId("passwordField");
        TextField visiblePasswordField = createVisibleField();
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setId("visiblePasswordField");

        Label confirmPasswordLabel = createLabel("Подтверждение:");
        PasswordField passwordConfirmField = createPasswordField();
        passwordConfirmField.setId("passwordConfirmField");

        Button showHideButton = new Button("Показать");
        showHideButton.setId("showHideButton");

        HBox passwordContainer = new HBox();
        passwordContainer.setSpacing(ROOT_SPACING);
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        HBox.setHgrow(visiblePasswordField, Priority.ALWAYS);
        passwordContainer.getChildren().addAll(
                passwordLabel,
                passwordField,
                visiblePasswordField,
                showHideButton
        );
        HBox confirmContainer = new HBox();
        confirmContainer.setSpacing(ROOT_SPACING);
        HBox.setHgrow(passwordConfirmField, Priority.ALWAYS);
        confirmContainer.getChildren().addAll(
                confirmPasswordLabel,
                passwordConfirmField
        );

        VBox root = new VBox();
        root.setSpacing(ROOT_SPACING);
        root.setPadding(ROOT_ALIGNMENT);
        root.getChildren().addAll(
                passwordContainer,
                confirmContainer,
                createSeparator(),
                createButtonContainer(okButton, exitButton)
        );

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        changPasswordStage.setScene(scene);
        changPasswordStage.setResizable(false);
        // установка привязок
        setupBindings(changPasswordStage);
        setupHandler(changPasswordStage);
        changPasswordStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setPrefWidth(100);
        return label;
    }

    private TextField createVisibleField() {
        TextField visiblePassword = new TextField();
        visiblePassword.setPromptText("Введите пароль");
        return visiblePassword;
    }

    private PasswordField createPasswordField(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");
        return passwordField;
    }

    private Separator createSeparator() {
        return new Separator();
    }

    private HBox createButtonContainer(Button okButton, Button exitButton){
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_RIGHT);
        container.setSpacing(ROOT_SPACING);
        container.getChildren().addAll(
                okButton, exitButton
        );
        return container;
    }

    private void setupHandler(Stage stage) {
        Parent root = stage.getScene().getRoot();
        Button exitButton = (Button) root.lookup("#exitButton");
        Button okButton = (Button) root.lookup("#okButton");
        Button showHideButton = (Button) root.lookup("#showHideButton");

        handler.checkPasswordField(stage);

        exitButton.setOnAction(event -> handler.closingEvent(stage));
        okButton.setOnAction(event -> handler.successfulEvent(stage));
        showHideButton.setOnAction(event -> handler.hideEvent(stage));
    }

    private void setupBindings(Stage stage) {

    }
}
