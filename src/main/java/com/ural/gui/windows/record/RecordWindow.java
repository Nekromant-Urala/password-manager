package com.ural.gui.windows.record;

import com.ural.gui.windows.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecordWindow implements Window {
    private static final EventHandlerRecordWindow handler = new EventHandlerRecordWindow();
    private static final int LABEL_WIDTH = 100;
    private static final int TEXT_FIELD_PASSWORD_WIDTH = 280;
    private static final int TEXT_FIELD_WIDTH = 250;
    private static final int BUTTON_WIDTH = 70;

    @Override
    public void createWindow(Stage owner) {
        Stage recordStage = new Stage();
        recordStage.setTitle("Добавление записи/пароля");

        // блокировка окна, на котором было вызвано данное окно
        recordStage.initModality(Modality.WINDOW_MODAL);
        recordStage.initOwner(owner);

        // поле для ввода названия пароля
        HBox recordContainer = new HBox();
        Label tittleRecord = new Label("Название");
        tittleRecord.setPrefWidth(LABEL_WIDTH);
        tittleRecord.setAlignment(Pos.CENTER_RIGHT);
        TextField recordField = new TextField();
        recordField.setPromptText("Введите название для записи");
        recordField.setPrefWidth(TEXT_FIELD_WIDTH);

        recordContainer.setSpacing(10);
        recordContainer.getChildren().addAll(
                tittleRecord,
                recordField
        );

        // поле для ввода логина-пароля
        HBox loginContainer = new HBox();
        Label tittleLogin = new Label("Логин:");
        tittleLogin.setPrefWidth(LABEL_WIDTH);
        tittleLogin.setAlignment(Pos.CENTER_RIGHT);
        TextField loginField = new TextField();
        loginField.setPromptText("Введите логин");
        loginField.setPrefWidth(TEXT_FIELD_WIDTH);

        loginContainer.setSpacing(10);
        loginContainer.setPadding(new Insets(10, 0, 0, 0));
        loginContainer.getChildren().addAll(
                tittleLogin,
                loginField
        );

        // группа пароля (ComboBox)
        HBox groupContainer = new HBox();
        Label tittleGroup = new Label("Группа:");
        tittleGroup.setPrefWidth(LABEL_WIDTH);
        tittleGroup.setAlignment(Pos.CENTER_RIGHT);
        TextField personalGroupField = new TextField();
        personalGroupField.setDisable(true);
        personalGroupField.setPromptText("Введите название группы");
        personalGroupField.setPrefWidth(TEXT_FIELD_WIDTH);

        ObservableList<String> groups = FXCollections.observableArrayList("Общие", "Сеть", "Интернет", "Почта", "Счета", "OC", "Другое");
        ComboBox<String> comboBoxGroup = new ComboBox<>(groups);
        comboBoxGroup.setValue(groups.get(0));
        comboBoxGroup.setPrefWidth(100);

        // Обработчик изменения выбора в комбобоксе
        handler.checkGroup(comboBoxGroup, personalGroupField);

        groupContainer.setSpacing(10);
        groupContainer.setPadding(new Insets(10, 0, 0, 0));
        groupContainer.getChildren().addAll(
                tittleGroup,
                personalGroupField,
                comboBoxGroup
        );

        // поле для ввода пароля
        HBox passwordContainer = new HBox();
        Label tittlePassword = new Label("Пароль:");
        tittlePassword.setPrefWidth(LABEL_WIDTH);
        tittlePassword.setAlignment(Pos.CENTER_RIGHT);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");
        passwordField.setPrefWidth(TEXT_FIELD_PASSWORD_WIDTH);

        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Введите пароль");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setPrefWidth(TEXT_FIELD_PASSWORD_WIDTH);

        // поле для подтверждения пароля
        HBox confirmContainer = new HBox();
        Label tittleConfirmPassword = new Label("Подтверждение:");
        tittleConfirmPassword.setPrefWidth(LABEL_WIDTH);
        tittleConfirmPassword.setAlignment(Pos.CENTER_RIGHT);
        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("Введите пароль");
        passwordConfirmField.setPrefWidth(TEXT_FIELD_PASSWORD_WIDTH);

        // кнопка скрыть/показать пароль
        Button showHideButton = new Button("Показать");
        showHideButton.setPrefSize(BUTTON_WIDTH, passwordField.getPrefHeight());
        showHideButton.setOnAction(e -> handler.showHideButton(showHideButton, passwordField, visiblePasswordField, passwordConfirmField));

        passwordContainer.setSpacing(10);
        passwordContainer.setPadding(new Insets(10, 0, 0, 0));
        passwordContainer.getChildren().addAll(
                tittlePassword,
                passwordField,
                visiblePasswordField,
                showHideButton
        );

        // кнопка для генерации пароля (вызывается окно генератора)
        Button generationButton = new Button("Создать");
        generationButton.setOnAction(event -> handler.generationButton(recordStage));
        generationButton.setPrefSize(BUTTON_WIDTH, showHideButton.getPrefHeight());

        confirmContainer.setPadding(new Insets(10, 0, 0, 0));
        confirmContainer.setSpacing(10);
        confirmContainer.getChildren().addAll(
                tittleConfirmPassword,
                passwordConfirmField,
                generationButton
        );

        // проверка совпадения паролей
        // handler.checkPasswordField(passwordField, visiblePasswordField, passwordConfirmField, nameFileField, pathFileField, okButton);

        // поле для ввода ссылки (сервиса) для которого создана запись
        HBox urlContainer = new HBox();
        Label tittleURL = new Label("Сервис:");
        tittleURL.setPrefWidth(LABEL_WIDTH);
        tittleURL.setAlignment(Pos.CENTER_RIGHT);
        TextField urlField = new TextField();
        urlField.setPrefWidth(TEXT_FIELD_WIDTH);
        urlField.setPromptText("Введите название/ссылку сервиса");

        urlContainer.setSpacing(10);
        urlContainer.setPadding(new Insets(10, 0, 0, 0));
        HBox.setHgrow(urlField, Priority.ALWAYS);
        urlContainer.getChildren().addAll(
                tittleURL,
                urlField
        );

        // поле для заметок
        HBox descriptionContainer = new HBox();
        Label description = new Label("Описание:");
        description.setPrefWidth(LABEL_WIDTH);
        description.setAlignment(Pos.CENTER_RIGHT);
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefSize(TEXT_FIELD_PASSWORD_WIDTH, 100);
        descriptionArea.setPromptText("Заметка, о пароле которую хотите оставить");


        descriptionContainer.setSpacing(10);
        descriptionContainer.setPadding(new Insets(10, 0, 0, 0));
        HBox.setHgrow(descriptionArea, Priority.ALWAYS);
        descriptionContainer.getChildren().addAll(
                description,
                descriptionArea
        );


        // Нижний разделитель
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 0, 0));

        // Кнопки внизу окна
        Button addRecordButton = new Button("Добавить");
        addRecordButton.setDisable(true);
        Button exitButton = new Button("Закрыть");
        HBox buttonsContainer = new HBox(10, addRecordButton, exitButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setPadding(new Insets(10, 0, 0, 0));
        addRecordButton.setOnAction(event -> handler.addRecordButton(recordStage));
        exitButton.setOnAction(event -> handler.exitWindow(recordStage));

        // Главный контейнер окна
        VBox root = new VBox();

        root.setPadding(new Insets(10, 15, 10, 15));
        root.getChildren().addAll(
                recordContainer,
                loginContainer,
                groupContainer,
                passwordContainer,
                confirmContainer,
                urlContainer,
                descriptionContainer,
                separator,
                buttonsContainer
        );

        Scene scene = new Scene(root, 500, 350);
        recordStage.setResizable(false);
        recordStage.setScene(scene);
        recordStage.show();
    }
}
