package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Главный контейнер
        VBox root = new VBox();
        root.setPadding(new Insets(15));
        root.setSpacing(15);
        root.setAlignment(Pos.CENTER_LEFT);

        // Заголовок
        Label titleLabel = new Label("Введите мастер-ключ");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Путь к файлу
        Label filePathLabel = new Label("путь до файла");
        filePathLabel.setStyle("-fx-text-fill: #555555;");

        // Поле для мастер-пароля
        Label masterPassLabel = new Label("Мастер-пароль:");
        PasswordField masterPassField = new PasswordField();
        masterPassField.setPromptText("Введите мастер-пароль");
        masterPassField.setMaxWidth(300);

        // Поле для ключевого файла
        Label keyFileLabel = new Label("Ключ файл/поставщик:");
        TextField keyFileField = new TextField("(Iter)");
        keyFileField.setMaxWidth(300);

        // Кнопки внизу
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        Button createDatabase = new Button("Создать базу данных");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Отмена");

        buttonBox.getChildren().addAll(createDatabase, okButton, cancelButton);

        // Добавляем все элементы в корневой контейнер
        root.getChildren().addAll(
                titleLabel,
                filePathLabel,
                new Separator(),
                masterPassLabel,
                masterPassField,
                keyFileLabel,
                keyFileField,
                new Separator(),
                buttonBox
        );

        // Настройка окна
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Ввод мастер-пароля");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Обработчики кнопок
        okButton.setOnAction(e -> {
            // Проверка пароля и переход к главному окну
            if (!masterPassField.getText().isEmpty()) {
                System.out.println("Пароль принят!");
                // Здесь можно открыть главное окно
                // new MainWindow().start(new Stage());
                primaryStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Не введён мастер-пароль");
                alert.showAndWait();
            }
        });

        cancelButton.setOnAction(e -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}