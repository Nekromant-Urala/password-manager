package com.ural.gui.windows.main;

import com.ural.gui.windows.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainWindow implements Window {
    private static final EventHandlerMainWindow handler = new EventHandlerMainWindow();
    private static final int COLUMN_WIDTH = 199;
    private static final String STYLE_ENTERED = "-fx-background-color: #e1f5fe;-fx-padding: 5px;-fx-border-radius: 3px;"; // светло-голубой
    private static final String STYLE_EXITED = "-fx-background-color: transparent;-fx-padding: 5px;-fx-border-radius: 3px;"; //
    private static final String STYLE_PRESSED = "-fx-background-color: #b3e5fc;-fx-padding: 5px;-fx-border-radius: 3px;";

    public void createWindow(Stage owner) {
        Stage mainWindow = new Stage();

        // Создание строки меню для взаимодействия с окном
        MenuBar menuBar = new MenuBar();
        createElementsMenuBar(menuBar, mainWindow);


        VBox topContainer = new VBox(menuBar);
        topContainer.setPadding(new Insets(0, 0, 2, 0));

        // Контейнер для взаимодействия с группами, на которые разделены пароли
        VBox groups = createListGroups();

        // Создаем список объектов
        ObservableList<PasswordRecord> records = FXCollections.observableArrayList(
        );


        // Контейнер для демонстрации и взаимодействия с записями (паролями)
        TableView<PasswordRecord> table = new TableView<>(records);
        table.setPrefSize(1000, 500);
        table.setStyle(
                "-fx-border-color: #a0a0a0;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 2px;" +
                        "-fx-border-insets: 2px;" +
                        "-fx-background-color: #FFFFFF;"
        );
        table.setPlaceholder(new Label("Нет записей для отображения"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);

        // Столбец для вывода названия записи
        TableColumn<PasswordRecord, String> nameColumn = createColumn("Название", "name");
        table.getColumns().add(nameColumn);

        // Столбец для вывода логина записи
        TableColumn<PasswordRecord, String> loginColumn = createColumn("Логин", "login");
        table.getColumns().add(loginColumn);

        // Столбец для вывода пароля
        TableColumn<PasswordRecord, String> passwordColumn = createColumn("Пароль", "password");
        table.getColumns().add(passwordColumn);

        // Столбец для вывода сервиса
        TableColumn<PasswordRecord, String> serviceColumn = createColumn("Сервис", "service");
        table.getColumns().add(serviceColumn);

        // Столбец для вывода заметок
        TableColumn<PasswordRecord, String> notionColumn = createColumn("Заметки", "notion");
        table.getColumns().add(notionColumn);

        // Поле для вывода подробной информации о записи
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
        root.setCenter(table);
        root.setBottom(hBox);


        Scene scene = new Scene(root, 1200, 700);
        mainWindow.setScene(scene);
        mainWindow.setTitle("Hell Spring");
        mainWindow.setResizable(false);
        mainWindow.show();
    }

    private static VBox createListGroups() {
        VBox groupList = new VBox();
        groupList.setPrefSize(200, 600);
        groupList.setStyle(
                "-fx-border-color: #a0a0a0;" +       // Цвет границы
                        "-fx-border-width: 1px;" +           // Толщина
                        "-fx-border-radius: 0px;" +          // закругление углов
                        "-fx-border-insets: 2px;" +          // отступ границы от краём
                        "-fx-padding: 2px;" +                // внутренний отступ
                        "-fx-background-color: #FFFFFF;"// фон внутри
        );

        Label allGroups = createLabelGroup("Все группы:");

        VBox groupsContainer = new VBox();
        Label general = createLabelGroup("Общие");
        Label network = createLabelGroup("Сеть");
        Label internet = createLabelGroup("Интернет");
        Label mail = createLabelGroup("Почта");
        Label account = createLabelGroup("Счета");
        Label oc = createLabelGroup("OC");

        groupsContainer.setPadding(new Insets(0, 0, 0, 50));
        groupsContainer.getChildren().addAll(
                general,
                network,
                internet,
                mail,
                account,
                oc
        );

        groupList.getChildren().addAll(
                allGroups,
                groupsContainer

        );

        return groupList;
    }

    private static Label createLabelGroup(String textLabel) {
        Label label = new Label(textLabel);
        label.setStyle(STYLE_EXITED);

        // вход в область
        label.setOnMouseEntered(mouseEvent -> label.setStyle(STYLE_ENTERED));
        // выход из области
        label.setOnMouseExited(e -> label.setStyle(STYLE_EXITED));
        // во время нажатия
        label.setOnMousePressed(e -> label.setStyle(STYLE_PRESSED));
        // после нажатия
        label.setOnMouseReleased(e -> label.setStyle(STYLE_EXITED));
        // действие при клике
        label.setOnMouseClicked(e -> System.out.println("Ого! Считан клик"));

        return label;
    }

    private static void createElementsMenuBar(MenuBar menuBar, Stage owner) {
        // Элементы строки меню
        Menu file = new Menu("Файл");
        Menu group = new Menu("Группа");
        Menu record = new Menu("Запись");
        Menu service = new Menu("Сервис");

        // Элементы каждого из меню
        MenuItem createNewDatabase = new MenuItem("Создать базу данных");
        MenuItem saveChanges = new MenuItem("Сохранить изменения");
        MenuItem parameterDatabase = new MenuItem("Текущие параметры");
        MenuItem editMasterPassword = new MenuItem("Изменить мастер-пароль");
        MenuItem exitItemMenu = new MenuItem("Выход");
        MenuItem addGroup = new MenuItem("Добавить группу");
        MenuItem editGroup = new MenuItem("Изменить группу");
        MenuItem deleteGroup = new MenuItem("Удалить группу");
        MenuItem addRecord = new MenuItem("Добавить записи");
        MenuItem generator = new MenuItem("Генератор");

        generator.setOnAction(event -> handler.openGeneratorPasswordWindow(owner));
        exitItemMenu.setOnAction(event -> handler.exitWindow(owner));
        addRecord.setOnAction(event -> handler.openRecordWindow(owner));

        // Добавление элементов в каждое меню
        file.getItems().addAll(
                createNewDatabase,
                new SeparatorMenuItem(),
                saveChanges,
                parameterDatabase,
                editMasterPassword,
                new SeparatorMenuItem(),
                exitItemMenu
        );

        group.getItems().addAll(
                addGroup,
                editGroup,
                new SeparatorMenuItem(),
                deleteGroup
        );
        record.getItems().addAll(
                addRecord
        );
        service.getItems().addAll(
                generator
        );
        menuBar.getMenus().addAll(
                file,
                group,
                record,
                service
        );
    }

    /**
     * @param nameColumn заголовок колонки
     * @param field      название поля класса, за которым необходимо закрепить колонку таблицы
     * @return Возвращает колонку для создания таблицы
     */
    private TableColumn<PasswordRecord, String> createColumn(String nameColumn, String field) {
        TableColumn<PasswordRecord, String> column = new TableColumn<>();
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        Label hader = new Label(nameColumn);
        hader.setAlignment(Pos.CENTER_LEFT);
        hader.setMaxWidth(Double.MAX_VALUE);
        column.setGraphic(hader);
        column.setPrefWidth(COLUMN_WIDTH);

        return column;
    }
}
