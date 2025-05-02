package com.ural.gui.windows.init;

import com.ural.gui.windows.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class InitialWindow implements Window {
    private static final EventHandlerInitialWindow handler = new EventHandlerInitialWindow();

    @Override
    public void createWindow(Stage owner) {
        // Главный контейнер
        VBox root = new VBox();
        root.setPadding(new Insets(10, 20, 10, 20));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER_LEFT);

        // Заголовок
        Label titleLabel = new Label("Введите мастер-ключ");
        Font titleFont = Font.font("", FontWeight.BOLD, 22);
        titleLabel.setFont(titleFont);

        // Путь к файлу
        Label filePathLabel = new Label("путь до файла");
        filePathLabel.setTextFill(Color.GRAY);

        // Поле для мастер-пароля
        Label masterPassLabel = new Label("Мастер-пароль:");
        PasswordField masterPassField = new PasswordField();
        masterPassField.setPromptText("Введите мастер-пароль");
        masterPassField.setPrefWidth(190);

        // Поле для отображения пароля
        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Введите мастер-пароль");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setPrefWidth(190);

        // Поле для ключевого файла с кнопкой выбора
        Label keyFileLabel = new Label("Ключ файл/поставщик:");
        TextField keyFileField = new TextField();
        keyFileField.setPromptText("Путь до файла, в котором находится пароль");

        // Кнопка выбора файла
        Button browseButton = new Button("Обзор...");
        browseButton.setPrefSize(70, keyFileField.getPrefHeight());
        browseButton.setOnAction(e -> handler.browseButton(owner, keyFileField));

        // Кнопка показа/скрытия пароля
        Button showHideButton = new Button("Показать");
        showHideButton.setPrefSize(browseButton.getPrefWidth(), masterPassField.getPrefHeight());
        showHideButton.setOnAction(e -> handler.showHideButton(showHideButton, masterPassField, visiblePasswordField));

        // Горизонтальный контейнер для кнопки и поля ввода пароля
        HBox passwordContainer = new HBox(10);
        passwordContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(masterPassField, Priority.ALWAYS);
        HBox.setHgrow(visiblePasswordField, Priority.ALWAYS);
        passwordContainer.getChildren().addAll(masterPassField, visiblePasswordField, showHideButton);

        // Горизонтальный контейнер для поля ввода и кнопки
        HBox keyFileContainer = new HBox(10);
        keyFileContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(keyFileField, Priority.ALWAYS);
        keyFileField.setPrefWidth(190);
        keyFileContainer.getChildren().addAll(keyFileField, browseButton);

        // Кнопки внизу - используем BorderPane для разделения левой и правой частей
        BorderPane buttonContainer = new BorderPane();
        buttonContainer.setPadding(new Insets(0, 0, 0, 0)); // Отступ сверху

        // Кнопка создания БД - слева
        Button createDatabase = new Button("Создать базу данных");
        HBox leftBox = new HBox(10, createDatabase);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        createDatabase.setOnAction(e -> handler.createDatabaseButton(owner));

        // Кнопки OK и Отмена - справа
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Отмена");
        HBox rightBox = new HBox(10, okButton, cancelButton);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        okButton.setOnAction(e -> handler.okButton(owner, masterPassField, visiblePasswordField));
        cancelButton.setOnAction(e -> handler.cancelButton(owner));

        // Размещаем элементы в BorderPane
        buttonContainer.setLeft(leftBox);
        buttonContainer.setRight(rightBox);

        root.getChildren().addAll(
                titleLabel,
                filePathLabel,
                new Separator(),
                masterPassLabel,
                passwordContainer,
                keyFileLabel,
                keyFileContainer,
                new Separator(),
                buttonContainer
        );

        // Настройка окна
        Scene scene = new Scene(root, 400, 270);
        owner.setTitle("Ввод мастер-пароля");
        owner.setScene(scene);
        owner.setResizable(false);
        owner.show();
    }
}