package com.ural.gui.windows.record;

import com.ural.gui.core.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    public RecordWindow() {
        this.handler = new RecordHandler();
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
        TextField tittleField = createTextField("Введите название для записи");
        tittleField.setId("tittleField");
        // контейнер названия
        HBox recordContainer = createContainer();
        recordContainer.getChildren().addAll(
                tittleLabel,
                tittleField
        );

        // поле для ввода логина-пароля
        Label loginLabel = createLabel("Логин:");
        TextField loginField = createTextField("Введите логин");
        loginField.setId("loginField");
        // контейнер логина
        HBox loginContainer = createContainer();
        loginContainer.getChildren().addAll(
                loginLabel,
                loginField
        );

        // группа пароля (ComboBox)
        Label groupLabel = createLabel("Группа:");
        TextField groupField = createTextField("Введите название группы");
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
        TextField visiblePasswordField = createPasswordField();
        visiblePasswordField.setId("passwordField");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        // поле для подтверждения пароля
        Label confirmPasswordLabel = createLabel("Подтверждение:");
        PasswordField passwordConfirmField = createPasswordField();
        passwordField.setId("passwordConfirmField");
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
        TextField urlField = createTextField("Введите название/ссылку сервиса");
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
        Button addRecordButton = new Button("Добавить");
        addRecordButton.setDisable(true);
        addRecordButton.setId("addRecordButton");
        Button exitButton = new Button("Закрыть");
        exitButton.setId("exitButton");
        // контейнер главных кнопок окна
        HBox buttonsContainer = new HBox(SPACING, addRecordButton, exitButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setPadding(TOP_MARGIN);

        setupHandler(addRecordButton, exitButton, showHideButton, generationButton, recordStage);

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

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(TEXT_FIELD_WIDTH);
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
        ComboBox<String> comboBox = new ComboBox<>(groups);
        comboBox.setValue(groups.get(0));
        comboBox.setPrefWidth(100);
        return comboBox;
    }

    private void setupHandler(Button addRecordButton, Button exitButton, Button showHideButton, Button generationButton, Stage stage) {
//        addRecordButton.setOnAction(event -> handler.addRecordButton(stage));
//        exitButton.setOnAction(event -> handler.exitWindow(stage));
//        showHideButton.setOnAction(e -> handler.showHideButton(showHideButton, passwordField, visiblePasswordField, passwordConfirmField));
//        generationButton.setOnAction(event -> handler.generationButton(stage));
//
//        handler.checkGroup(comboBoxGroup, personalGroupField);
    }

    private void setupBindings() {

    }
}
