package com.ural.gui.windows.record;

import com.ural.gui.core.Window;
import com.ural.manager.model.PasswordEntre;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class RecordWindow implements Window {
    private static final int LABEL_WIDTH = 100;
    private static final int TEXT_FIELD_PASSWORD_WIDTH = 280;
    private static final int TEXT_FIELD_WIDTH = 250;
    private static final int BUTTON_WIDTH = 70;
    private static final int WIDTH_WINDOW = 500;
    private static final int HEIGHT_WINDOW = 350;
    private static final Insets TOP_MARGIN = new Insets(10, 0, 0, 0);
    private static final Insets ROOT_MARGIN = new Insets(10, 15, 10, 15);
    private static final int SPACING = 10;

    private final RecordHandler handler;
    private final PasswordEntre.Builder passwordEntre;

    public RecordWindow() {
        this.handler = new RecordHandler();
        this.passwordEntre = new PasswordEntre.Builder();
    }

    @Override
    public void createWindow(Stage stage) {
        Stage recordStage = new Stage();
        recordStage.setTitle("Добавление записи/пароля");

        // блокировка окна, на котором было вызвано данное окно
        recordStage.initModality(Modality.WINDOW_MODAL);
        recordStage.initOwner(stage);
        // поле для ввода названия пароля
        Label tittleLabel = createLabel("Название");
        TextField tittleField = createTextField("Введите название для записи", TEXT_FIELD_WIDTH);
        tittleField.setId("tittleField");
        // контейнер названия
        HBox recordContainer = createContainer();
        recordContainer.getChildren().addAll(
                tittleLabel,
                tittleField
        );

        // поле для ввода логина-пароля
        Label loginLabel = createLabel("Логин:");
        TextField loginField = createTextField("Введите логин", TEXT_FIELD_WIDTH);
        loginField.setId("loginField");
        // контейнер логина
        HBox loginContainer = createContainer();
        loginContainer.getChildren().addAll(
                loginLabel,
                loginField
        );

        // группа пароля (ComboBox)
        Label groupLabel = createLabel("Группа:");
        TextField groupField = createTextField("Введите название группы", TEXT_FIELD_WIDTH);
        groupField.setId("groupField");
        groupField.setDisable(true);
        // комбобокс со списком допустимых групп
        ComboBox<String> comboBoxGroup = createListGroups();
        comboBoxGroup.setId("comboBoxGroup");
        // контейнер группы
        HBox groupContainer = createContainer();
        groupContainer.getChildren().addAll(
                groupLabel,
                groupField,
                comboBoxGroup
        );

        // поле для ввода пароля
        Label passwordLabel = createLabel("Пароль:");
        PasswordField passwordField = createPasswordField();
        passwordField.setId("passwordField");

        TextField visiblePasswordField = createTextField("Введите пароль", TEXT_FIELD_PASSWORD_WIDTH);
        visiblePasswordField.setId("visiblePasswordField");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        // поле для подтверждения пароля
        Label confirmPasswordLabel = createLabel("Подтверждение:");
        PasswordField passwordConfirmField = createPasswordField();
        passwordConfirmField.setId("passwordConfirmField");
        // кнопка скрыть/показать пароль
        Button showHideButton = new Button("Показать");
        showHideButton.setId("showHideButton");
        showHideButton.setPrefSize(BUTTON_WIDTH, passwordField.getPrefHeight());
        // контейнер поля ввода пароля
        HBox passwordContainer = createContainer();
        passwordContainer.getChildren().addAll(
                passwordLabel,
                passwordField,
                visiblePasswordField,
                showHideButton
        );

        // кнопка для генерации пароля (вызывается окно генератора)
        Button generationButton = new Button("Создать");
        generationButton.setId("generationButton");
        generationButton.setPrefSize(BUTTON_WIDTH, showHideButton.getPrefHeight());
        // контейнер подтверждения пароля
        HBox confirmContainer = createContainer();
        confirmContainer.getChildren().addAll(
                confirmPasswordLabel,
                passwordConfirmField,
                generationButton
        );

        // поле для ввода ссылки (сервиса) для которого создана запись
        Label urlLabel = createLabel("Сервис:");
        TextField urlField = createTextField("Введите название/ссылку сервиса", TEXT_FIELD_WIDTH);
        urlField.setId("urlField");
        // контейнер ссылки
        HBox urlContainer = createContainer();
        HBox.setHgrow(urlField, Priority.ALWAYS);
        urlContainer.getChildren().addAll(
                urlLabel,
                urlField
        );

        // поле для заметок
        Label descriptionLabel = createLabel("Описание:");
        TextArea descriptionArea = createTextArea();
        descriptionArea.setId("descriptionArea");
        // контейнер описания
        HBox descriptionContainer = createContainer();
        HBox.setHgrow(descriptionArea, Priority.ALWAYS);
        descriptionContainer.getChildren().addAll(
                descriptionLabel,
                descriptionArea
        );

        // Кнопки внизу окна
        Button okButton = new Button("Добавить");
        okButton.setDisable(true);
        okButton.setId("okButton");
        Button exitButton = new Button("Закрыть");
        exitButton.setId("exitButton");
        // контейнер главных кнопок окна
        HBox buttonsContainer = new HBox(SPACING, okButton, exitButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setPadding(TOP_MARGIN);

        // Главный контейнер окна
        VBox root = new VBox();
        root.setPadding(ROOT_MARGIN);
        root.getChildren().addAll(
                recordContainer,
                loginContainer,
                groupContainer,
                passwordContainer,
                confirmContainer,
                urlContainer,
                descriptionContainer,
                createSeparator(),
                buttonsContainer
        );

        Scene scene = new Scene(root, WIDTH_WINDOW, HEIGHT_WINDOW);
        recordStage.setResizable(false);
        recordStage.setScene(scene);
        // установка обработчика
        setupBindings(recordStage);
        setupHandler(recordStage);
        recordStage.show();
    }

    private HBox createContainer() {
        HBox hBox = new HBox();
        hBox.setSpacing(SPACING);
        hBox.setPadding(TOP_MARGIN);
        return hBox;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(TOP_MARGIN);
        return separator;
    }

    private TextArea createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setPrefSize(TEXT_FIELD_PASSWORD_WIDTH, 100);
        textArea.setPromptText("Заметка, о пароле которую хотите оставить");
        return textArea;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setPrefWidth(LABEL_WIDTH);
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }

    private TextField createTextField(String promptText, int width) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(width);
        return textField;
    }

    private PasswordField createPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");
        passwordField.setPrefWidth(TEXT_FIELD_PASSWORD_WIDTH);
        return passwordField;
    }

    private ComboBox<String> createListGroups() {
        ObservableList<String> groups = FXCollections.observableArrayList("Общие", "Сеть", "Интернет", "Почта", "Счета", "OC", "Другое");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(groups);
        comboBox.setValue(groups.get(0));
        comboBox.setPrefWidth(100);
        return comboBox;
    }

    private void setupHandler(Stage stage) {
        Parent root = stage.getScene().getRoot();
        Button okButton = (Button) root.lookup("#okButton");
        Button exitButton = (Button) root.lookup("#exitButton");
        Button showHideButton = (Button) root.lookup("#showHideButton");
        Button generationButton = (Button) root.lookup("#generationButton");

        handler.checkGroup(stage);
        handler.checkPasswordField(stage);

        okButton.setOnAction(event -> {
            handler.setPasswordEntre(passwordEntre);
            handler.successfulEvent(stage);
        });
        exitButton.setOnAction(event -> handler.closingEvent(stage));
        showHideButton.setOnAction(event -> handler.hideEvent(stage));
        generationButton.setOnAction(event -> handler.generationEvent(stage));
    }

    private void setupBindings(Stage stage) {
        Parent root = stage.getScene().getRoot();
        TextField tittleField = (TextField) root.lookup("#tittleField");
        TextField loginField = (TextField) root.lookup("#loginField");
        TextField groupField = (TextField) root.lookup("#groupField");
        ComboBox<String> comboBoxGroup = (ComboBox<String>) root.lookup("#comboBoxGroup");
        PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
        TextField visiblePasswordField = (TextField) root.lookup("#visiblePasswordField");
        TextField urlField = (TextField) root.lookup("#urlField");
        TextArea descriptionArea = (TextArea) root.lookup("#descriptionArea");

        tittleField.textProperty().addListener((obs, oldValue, newValue) -> {
            passwordEntre.addName(newValue);
        });
        loginField.textProperty().addListener((obs, oldValue, newValue) -> {
            passwordEntre.addLogin(newValue);
        });
        passwordField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (passwordField.isVisible()) {
                passwordEntre.addEncryptPassword(newValue);
            }
        });
        visiblePasswordField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (visiblePasswordField.isVisible()) {
                passwordEntre.addEncryptPassword(newValue);
            }
        });
        urlField.textProperty().addListener((obs, oldValue, newValue) -> {
            passwordEntre.addService(newValue);
        });
        descriptionArea.textProperty().addListener((obs, oldValue, newValue) -> {
            passwordEntre.addNotion(newValue);
        });
        ChangeListener<String> groupListener = (obs, oldValue, newValue) -> {
            String selectedGroup = groupField.isDisabled()
                    ? comboBoxGroup.getValue()
                    : groupField.getText();

            if (selectedGroup != null && !selectedGroup.isEmpty()) {
                passwordEntre.addGroup(selectedGroup);
            }
        };
        comboBoxGroup.valueProperty().addListener(groupListener);
        groupField.textProperty().addListener(groupListener);
        groupListener.changed(null, null, null);
    }
}
