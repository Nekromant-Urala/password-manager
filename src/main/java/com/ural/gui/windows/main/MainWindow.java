package com.ural.gui.windows.main;

import com.ural.gui.windows.Window;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow implements Window {
    private static final EventHandlerMainWindow handler = new EventHandlerMainWindow();

    public void createWindow(Stage owner) {
        Stage mainWindow = new Stage();

        // Создание строки меню для взаимодействия с окном
        MenuBar menuBar = new MenuBar();
        createElementsMenuBar(menuBar);


        VBox topContainer = new VBox(menuBar);
        topContainer.setPadding(new Insets(0, 0, 2, 0));

        // Контейнер для взаимодействия с группами, на которые разделены пароли
        VBox groups = new VBox();
        groups.setPrefSize(300, 600);
        groups.setStyle(
                "-fx-border-color: #a0a0a0;" +       // Цвет границы
                        "-fx-border-width: 1px;" +           // Толщина
                        "-fx-border-radius: 0px;" +          // закругление углов
                        "-fx-border-insets: 2px;" +          // отступ границы от краём
                        "-fx-padding: 2px;" +                // внутренний отступ
                        "-fx-background-color: #FFFFFF;"// фон внутри
        );

        Label label = new Label("Label1");
        // при вхождении курсора в область
        label.setOnMouseEntered(mouseEvent -> {
            label.setStyle(
                    "-fx-background-color: #e1f5fe;" +  // светло-голубой
                            "-fx-padding: 5px;" +
                            "-fx-border-radius: 3px;"
            );
        });

        // при выходе курсора из области
        label.setOnMouseExited(e -> {
            label.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-padding: 5px;" +
                            "-fx-border-radius: 3px;"
            );
        });

        // Действие во время нажатия
        label.setOnMousePressed(e -> {
            label.setStyle(
                    "-fx-background-color: #b3e5fc;" +  // темнее светло-голубого
                            "-fx-padding: 5px;" +
                            "-fx-border-radius: 3px;"
            );
        });

        // Действие после нажатия
        label.setOnMouseReleased(e -> {
            label.setStyle(
                    "-fx-background-color: #e1f5fe;" +  // возвращаем цвет наведения
                            "-fx-padding: 5px;" +
                            "-fx-border-radius: 3px;"
            );
        });

        label.setOnMouseClicked(e -> {
            System.out.println("Кликнули по Сети");
        });

        label.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 5px;" +
                "-fx-border-radius: 3px;");


        groups.getChildren().addAll(
                label
        );


        // Контейнер для демонстрации и взаимодействия с записями (паролями)
        TableView<String> tableOfPasswords = new TableView<>();
        tableOfPasswords.setPrefSize(900, 500);
        tableOfPasswords.setStyle(
                "-fx-border-color: #a0a0a0;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 2px;" +
                        "-fx-border-insets: 2px;" +
                        "-fx-background-color: #FFFFFF;"
        );

        // Заглушки
        HBox hBox = new HBox();
        hBox.setPrefSize(1200, 100);
        hBox.setStyle(
                "-fx-border-color: #a0a0a0;" +        // Цвет границы
                        "-fx-border-width: 1px;" +           // Толщина
                        "-fx-border-radius: 0px;" +          // закругление углов
                        "-fx-border-insets: 2px;" +          // отступ границы от краём
                        "-fx-padding: 2px;" +                // внутренний отступ
                        "-fx-background-color: #FFFFFF;"// фон внутри
        );

        // Главный контейнер данного окна
        BorderPane root = new BorderPane();


        root.setTop(topContainer);
        root.setLeft(groups);
        root.setCenter(tableOfPasswords);
        root.setBottom(hBox);


        Scene scene = new Scene(root, 1200, 700);
        mainWindow.setScene(scene);
        mainWindow.setTitle("MainWindow");
        mainWindow.setResizable(false);
        mainWindow.show();
    }

    private static void createElementsMenuBar(MenuBar menuBar) {
        // Элементы строки меню
        Menu file = new Menu("Файл");
        Menu group = new Menu("Группа");
        Menu record = new Menu("Запись");
        Menu search = new Menu("Поиск");
        Menu view = new Menu("Вид");
        Menu service = new Menu("Сервис");

        // Элементы каждого из меню
        MenuItem item1 = new MenuItem("itemitemitemitem");
        MenuItem item2 = new MenuItem("item");
        MenuItem item3 = new MenuItem("item");
        MenuItem item4 = new MenuItem("item");
        MenuItem item5 = new MenuItem("item");
        MenuItem item6 = new MenuItem("item");
        MenuItem item7 = new MenuItem("item");
        MenuItem item8 = new MenuItem("item");
        MenuItem item9 = new MenuItem("item");
        MenuItem item10 = new MenuItem("item");
        MenuItem item11 = new MenuItem("item");

        // Добавление элементов в каждое меню
        file.getItems().addAll(
                item1,
                item2,
                item3,
                item4,
                new SeparatorMenuItem(),
                item5
        );

        group.getItems().addAll(
                item6,
                item7
        );
        record.getItems().addAll(
                item8
        );
        search.getItems().addAll(
                item9
        );
        view.getItems().addAll(
                item10
        );
        service.getItems().addAll(
                item11
        );
        menuBar.getMenus().addAll(
                file,
                group,
                record,
                search,
                view,
                service
        );
    }
}
