package gui.create;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateDBWindow {
    public static void showCreateDbWindow(Stage owner) {
        // Окно создания БД
        Stage createDbStage = new Stage();
        createDbStage.setTitle("Создание новой базы данных");

        createDbStage.initModality(Modality.WINDOW_MODAL);
        createDbStage.initOwner(owner);

        // Создаем вкладки
        TabPane tabPane = new TabPane();

        // Вкладка 1: Основные параметры
        Tab basicTab = new Tab("Основные");
        VBox basicContent = new VBox();

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
        showHideButton.setOnAction(e -> EventHandlerDBWindow.showHideButtonHandler(showHideButton, passwordField, visiblePasswordField, passwordConfirmField));

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
        browseButton.setOnAction(e -> EventHandlerDBWindow.browseButtonHandler(createDbStage, pathFileField));

        // Горизонтальный контейнер для выбора директории
        HBox browseContainer = new HBox();
        browseContainer.setPadding(new Insets(5, 10, 0, 15));
        browseContainer.setSpacing(10);
        browseContainer.setAlignment(Pos.CENTER_LEFT);
        browseContainer.getChildren().addAll(pathFileField, browseButton);

        // Описание для базы данных
        basicContent.getChildren().addAll(
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
        basicTab.setContent(basicContent);

        // Вкладка 2: Параметры шифрования
        Tab securityTab = new Tab("Безопасность");
        GridPane securityContent = new GridPane();
        securityContent.setVgap(10);
        securityContent.setHgap(10);
        securityContent.addRow(0, new Label("Алгоритм шифрования:"),
                new ComboBox<>(FXCollections.observableArrayList("AES-256", "Twofish", "ChaCha20")));
        securityContent.addRow(1, new Label("Количество итераций:"),
                new Spinner<>(1000, 1000000, 60000, 1000));
        securityTab.setClosable(false);
        securityTab.setContent(securityContent);

        // Вкладка 3: Дополнительно
        Tab advancedTab = new Tab("Дополнительно");
        advancedTab.setContent(new VBox(10,
                new CheckBox("Создать резервную копию"),
                new CheckBox("Сжать базу данных")
        ));
        advancedTab.setClosable(false);

        // Добавляем вкладки
        tabPane.getTabs().addAll(basicTab, securityTab, advancedTab);

        // Кнопки внизу окна
        Button okButton = new Button("Создать");
        Button cancelButton = new Button("Отмена");
        HBox buttons = new HBox(10, okButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(10, 15, 5, 15));

        // Основной контейнер
        VBox root = new VBox();

        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 15, 0, 15));

        root.getChildren().addAll(
                tabPane,
                separator,
                buttons
        );

        // Обработчики кнопок
        okButton.setOnAction(e -> {
            System.out.println("Создаем новую БД...");
            createDbStage.close();
        });

        cancelButton.setOnAction(e -> createDbStage.close());

        Scene scene = new Scene(root, 450, 315);
        createDbStage.setScene(scene);
        createDbStage.setResizable(false);
        createDbStage.show();
    }
}
