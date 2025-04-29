package com.ural.gui.windows.db;

import com.ural.gui.windows.Window;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DatabaseWindow implements Window {
    private static final EventHandlerDBWindow handler = new EventHandlerDBWindow();

    public void createWindow(Stage owner) {
        // Окно создания БД
        Stage createDbStage = new Stage();
        createDbStage.setTitle("Создание новой базы данных");

        createDbStage.initModality(Modality.WINDOW_MODAL);
        createDbStage.initOwner(owner);

        // Создаем вкладки
        TabPane tabPane = new TabPane();

        // Кнопки внизу окна
        Button okButton = new Button("Создать");
        Button cancelButton = new Button("Отмена");
        HBox buttons = new HBox(10, okButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(10, 15, 10, 15));

        // Вкладка 1: Основные параметры
        Tab basicTab = createTabBasicParameter(owner, okButton);

        // Вкладка 2: Параметры шифрования
        Tab securityTab = createTabSecurity();

        // Вкладка 3: Дополнительно
        Tab advancedTab = createTabAdvanced();

        // Добавляем вкладки
        tabPane.getTabs().addAll(basicTab, securityTab, advancedTab);

        // Обработчики кнопок
//        okButton.setDisable(true);
        okButton.setOnAction(e -> handler.okButtonHandler(createDbStage, okButton));

        cancelButton.setOnAction(e -> handler.cancelButtonHandler(createDbStage));

        // Основной контейнер
        VBox root = new VBox();
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 15, 0, 15));

        root.getChildren().addAll(
                tabPane,
                separator,
                buttons
        );

        Scene scene = new Scene(root, 450, 315);
        createDbStage.setScene(scene);
        createDbStage.setResizable(false);
        createDbStage.show();
    }

    private static Tab createTabAdvanced() {
        Tab advancedTab = new Tab("Дополнительно");

        // Главный контейнер вкладки "Дополнительно"
        VBox content = new VBox();

        // Заголовок
        Label tittle = new Label("Добавьте описание для базы данных.");

        // Поле для ввода текста описания БД
        TextArea description = new TextArea();
        description.setPromptText("Описание базы данных");
        description.setPrefSize(250, 170);
        description.setPrefColumnCount(20);
        description.setPrefRowCount(5);

        content.setPadding(new Insets(10, 15, 0, 15));
        content.setSpacing(10);
        content.getChildren().addAll(
                tittle,
                new Separator(),
                description
        );

        advancedTab.setClosable(false);
        advancedTab.setContent(content);
        return advancedTab;
    }

    private static Tab createTabBasicParameter(Stage owner, Button okButton) {
        // Вкладка 1: Основные параметры
        Tab basicTab = new Tab("Основные");

        // Строка для ввода мастер-пароля
        Label masterPassword = new Label("Введите мастер пароль:");
        masterPassword.setPadding(new Insets(10, 0, 0, 15));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите мастер-пароль");
        passwordField.setPrefWidth(300);

        // Поле для отображения пароля
        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Введите мастер-пароль");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setPrefWidth(300);

        // Строка для ввода, чтобы подтвердить ввод для мастер-пароля
        Label masterConfirmPassword = new Label("Подтвердите мастер пароль:");
        masterConfirmPassword.setPadding(new Insets(10, 0, 0, 15));
        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("Введите мастер-пароль");
        passwordConfirmField.setPrefWidth(300);

        // Кнопка для показа/скрытия пароля
        Button showHideButton = new Button("Показать");
        showHideButton.setPrefSize(70, passwordField.getPrefHeight());
        showHideButton.setOnAction(e -> handler.showHideButtonHandler(showHideButton, passwordField, visiblePasswordField, passwordConfirmField));

        // Горизонтальный контейнер для подтверждения пароля
        HBox confirmPasswordContainer = new HBox();
        confirmPasswordContainer.setPadding(new Insets(5, 10, 0, 15));
        confirmPasswordContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(masterConfirmPassword, Priority.ALWAYS);
        confirmPasswordContainer.getChildren().addAll(passwordConfirmField);

        // Горизонтальный контейнер для ввода пароля
        HBox passwordContainer = new HBox();
        passwordContainer.setPadding(new Insets(5, 10, 0, 15));
        passwordContainer.setSpacing(10);
        passwordContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(masterPassword, Priority.ALWAYS);
        passwordContainer.getChildren().addAll(passwordField, visiblePasswordField, showHideButton);

        // Строка ввода для имени базы данных (название файла)
        Label nameFile = new Label("Введите название для базы данных:");
        nameFile.setPadding(new Insets(10, 0, 0, 15));
        TextField nameFileField = new TextField();
        nameFileField.setPromptText("Название файла");
        nameFileField.setPrefWidth(300);

        // Горизонтальный контейнер для ввода имени файла
        HBox nameFileContainer = new HBox();
        nameFileContainer.setPadding(new Insets(5, 10, 0, 15));
        nameFileContainer.setSpacing(10);
        nameFileContainer.setAlignment(Pos.CENTER_LEFT);
        nameFileContainer.getChildren().addAll(nameFileField);

        // Строка ввода для пути, по которому хранить бд
        Label pathFile = new Label("Введите путь, по которому необходимо хранить файл:");
        pathFile.setPadding(new Insets(10, 0, 0, 15));
        TextField pathFileField = new TextField();
        pathFileField.setPromptText("Путь по которому будет храниться файл");
        pathFileField.setPrefWidth(300);

        Button browseButton = new Button("Обзор...");
        browseButton.setPrefSize(70, pathFileField.getPrefHeight());
        browseButton.setOnAction(e -> handler.browseButtonHandler(owner, pathFileField));

        // проверка на заполнение полей
//        EventHandlerDBWindow.checkPasswordField(
//                passwordField, visiblePasswordField, passwordConfirmField, nameFileField, pathFileField
//        );
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() ->
                        !passwordField.getText().isEmpty() &&
                                !passwordConfirmField.getText().isEmpty() &&
                                !nameFileField.getText().isEmpty() &&
                                !pathFileField.getText().isEmpty() &&
                                passwordField.getText().equals(passwordConfirmField.getText()),
                passwordField.textProperty(),
                passwordConfirmField.textProperty(),
                nameFileField.textProperty(),
                pathFileField.textProperty()
        );

        okButton.disableProperty().bind(allFieldsFilled.not());

        // Горизонтальный контейнер для выбора директории
        HBox browseContainer = new HBox();
        browseContainer.setPadding(new Insets(5, 10, 0, 15));
        browseContainer.setSpacing(10);
        browseContainer.setAlignment(Pos.CENTER_LEFT);
        browseContainer.getChildren().addAll(pathFileField, browseButton);

        // Главный контейнер основной вкладки
        VBox content = new VBox();

        // Описание для базы данных
        content.getChildren().addAll(
                masterPassword,
                passwordContainer,
                masterConfirmPassword,
                confirmPasswordContainer,
                nameFile,
                nameFileContainer,
                pathFile,
                browseContainer
        );
        basicTab.setClosable(false);
        basicTab.setContent(content);
        return basicTab;
    }

    private static Tab createTabSecurity() {
        Tab securityTab = new Tab("Безопасность");

        // Описание вкладки
        Label labelDescription = new Label("Здесь можно настроить параметры безопасности на уровне файлов.");
        labelDescription.setPadding(new Insets(10, 0, 0, 15));

        // Выбор алгоритма шифрования
        HBox securityAlgorithmsContainer = new HBox();

        Label labelTitleSec = new Label("Способ шифрования");
        Font titleFont = Font.font("", FontWeight.BOLD, 13);
        labelTitleSec.setFont(titleFont);
        labelTitleSec.setPadding(new Insets(5, 0, 0, 15));
        Label labelSecAlg = new Label("Алгоритм шифрования базы данных: ");
        ObservableList<String> algorithms = FXCollections.observableArrayList("AES-256", "ChaCha20");
        ComboBox<String> comboBoxAlgorithms = new ComboBox<>(algorithms);
        comboBoxAlgorithms.setValue("AES-256");
        comboBoxAlgorithms.setPrefWidth(150);

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        securityAlgorithmsContainer.setPadding(new Insets(5, 15, 0, 15));
        securityAlgorithmsContainer.getChildren().addAll(
                labelSecAlg,
                spacer1,
                comboBoxAlgorithms
        );

        // Выбор количества итераций и алгоритма создания ключа
        HBox keyTransformationContainer1 = new HBox();
        HBox keyTransformationContainer2 = new HBox();

        Label keyTransformationLabel = new Label("Трансформация ключа");
        Font keyTransformationTitleFont = Font.font("", FontWeight.BOLD, 13);
        keyTransformationLabel.setFont(keyTransformationTitleFont);
        keyTransformationLabel.setPadding(new Insets(5, 0, 0, 15));

        Label keyTransformationText = new Label("Функция формирования ключа: ");
        ObservableList<String> keyTransformationAlgorithms = FXCollections.observableArrayList("AES-PBKDF2", "Argon2");
        ComboBox<String> comboBoxKeyTransformationAlgorithms = new ComboBox<>(keyTransformationAlgorithms);
        comboBoxKeyTransformationAlgorithms.setValue("AES-PBKDF2");
        comboBoxKeyTransformationAlgorithms.setPrefWidth(150);

        Label countIteration = new Label("Количество итераций: ");
        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory<>() {
            @Override
            public void decrement(int step) {
                setValue(getValue() - 1_000);
            }

            @Override
            public void increment(int step) {
                setValue(getValue() + 1_000);
            }
        };
        factory.setValue(600_000);
        spinner.setValueFactory(factory);
        spinner.setEditable(true);

        spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(oldValue);
            }
        });
        spinner.setPrefWidth(150);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Region spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);

        keyTransformationContainer1.getChildren().addAll(
                keyTransformationText,
                spacer2,
                comboBoxKeyTransformationAlgorithms
        );
        keyTransformationContainer2.getChildren().addAll(
                countIteration,
                spacer3,
                spinner
        );

        // главный контейнер для выбора функции формирования ключа
        VBox vBoxSecurity = new VBox();
        Text description = new Text("Мастер-ключ преобразуется с помощью функции формирования ключа,\nчто добавляет вычисления и усложняет атаки по словарю и угадывание.");
        vBoxSecurity.setSpacing(10);
        vBoxSecurity.setPadding(new Insets(0, 15, 0, 15));
        vBoxSecurity.getChildren().addAll(
                description,
                keyTransformationContainer1,
                keyTransformationContainer2
        );

        // главный контейнер второй вкладки (вкладки безопасности)
        VBox content = new VBox();

        // разграничители
        Separator separator1SecTab = new Separator();
        separator1SecTab.setPadding(new Insets(5, 15, 0, 15));
        Separator separator2SecTab = new Separator();
        separator2SecTab.setPadding(new Insets(10, 15, 0, 15));

        content.getChildren().addAll(
                labelDescription,
                separator1SecTab,
                labelTitleSec,
                securityAlgorithmsContainer,
                separator2SecTab,
                keyTransformationLabel,
                vBoxSecurity
        );
        securityTab.setClosable(false);
        securityTab.setContent(content);
        return securityTab;
    }
}
