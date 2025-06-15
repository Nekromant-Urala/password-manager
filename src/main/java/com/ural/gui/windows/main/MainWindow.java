package com.ural.gui.windows.main;

import com.ural.gui.core.Window;
import com.ural.manager.model.PasswordEntre;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow implements Window {
    private final MainHandler handler;
    private static final int COLUMN_WIDTH = 200;

    private static final String STYLE_ENTERED = "-fx-background-color: #e1f5fe;-fx-padding: 5px;-fx-border-radius: 3px;"; // светло-голубой
    private static final String STYLE_EXITED = "-fx-background-color: transparent;-fx-padding: 5px;-fx-border-radius: 3px;"; //
    private static final String STYLE_PRESSED = "-fx-background-color: #b3e5fc;-fx-padding: 5px;-fx-border-radius: 3px;";
    private static final String STYLE_BORDER_CONTAINER = "-fx-border-color: #a0a0a0;-fx-border-width: 1px;-fx-padding: 2px;-fx-border-insets: 2px;-fx-background-color: #FFFFFF;";

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;


    public MainWindow() {
        this.handler = new MainHandler();
    }

    @Override
    public void createWindow(Stage stage) {
        Stage mainWindow = new Stage();

        // Создание строки меню для взаимодействия с окном
        MenuBar menuBar = new MenuBar();
        createElementsMenuBar(menuBar, mainWindow);


        VBox topContainer = new VBox(menuBar);
        topContainer.setPadding(new Insets(0, 0, 2, 0));

        // Контейнер для взаимодействия с группами, на которые разделены пароли
        VBox groups = createListGroups();

        // Создаем список объектов
        ObservableList<PasswordEntre> records = FXCollections.observableArrayList();

        // Контейнер для демонстрации и взаимодействия с записями (паролями)
        TableView<PasswordEntre> table = new TableView<>(records);
        table.setId("table");
        table.setPrefSize(1000, 500);
        table.setStyle(STYLE_BORDER_CONTAINER);
        table.setPlaceholder(new Label("Нет записей для отображения"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);

        // Столбец для вывода названия записи
        setColumn(table, "Название", "name");
        // Столбец для вывода логина записи
        setColumn(table, "Логин", "login");
        // Столбец для вывода пароля
        setColumn(table, "Пароль", "password");
        // Столбец для вывода сервиса
        setColumn(table, "Сервис", "service");
        // Столбец для вывода заметок
        setColumn(table, "Заметки", "notion");

        // Поле для вывода подробной информации о записи
        HBox hBox = new HBox();
        hBox.setPrefSize(1200, 100);
        hBox.setStyle(STYLE_BORDER_CONTAINER);

        // Главный контейнер данного окна
        BorderPane root = new BorderPane();

        root.setTop(topContainer);
        root.setLeft(groups);
        root.setCenter(table);
        root.setBottom(hBox);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setScene(scene);
        mainWindow.setTitle("Hell Spring");
        mainWindow.setResizable(false);
        // установка обработчика
        setupHandler(mainWindow);
        setupContextMenu(mainWindow);
        setupActionOnLabel(mainWindow);
        mainWindow.show();
    }

    private void setColumn(TableView<PasswordEntre> table, String nameColumn, String field) {
        TableColumn<PasswordEntre, String> notionColumn;
        if (nameColumn.equals("Пароль")) {
            notionColumn = createPasswordColumn(nameColumn, field);
        } else {
            notionColumn = createColumn(nameColumn, field);
        }
        table.getColumns().add(notionColumn);
    }

    private VBox createListGroups() {
        VBox groupList = new VBox();
        groupList.setPrefSize(200, 600);
        groupList.setStyle(STYLE_BORDER_CONTAINER);

        Label allGroups = createLabelGroup("Все группы:");
        allGroups.setId("allGroups");

        VBox groupsContainer = new VBox();
        Label general = createLabelGroup("Общие");
        general.setId("general");
        Label network = createLabelGroup("Сеть");
        network.setId("network");
        Label internet = createLabelGroup("Интернет");
        internet.setId("internet");
        Label mail = createLabelGroup("Почта");
        mail.setId("mail");
        Label account = createLabelGroup("Счета");
        account.setId("account");
        Label oc = createLabelGroup("OC");
        oc.setId("oc");
        Label other = createLabelGroup("Другое");
        other.setId("other");

        groupsContainer.setPadding(new Insets(0, 0, 0, 50));
        groupsContainer.getChildren().addAll(
                general,
                network,
                internet,
                mail,
                account,
                oc,
                other
        );

        groupList.getChildren().addAll(
                allGroups,
                groupsContainer

        );

        return groupList;
    }

    private Label createLabelGroup(String textLabel) {
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

        return label;
    }

    private void setupActionOnLabel(Stage stage) {
        Parent root = stage.getScene().getRoot();

        Label allGroups = (Label) root.lookup("#allGroups");
        Label general = (Label) root.lookup("#general");
        Label network = (Label) root.lookup("#network");
        Label internet = (Label) root.lookup("#internet");
        Label mail = (Label) root.lookup("#mail");
        Label account = (Label) root.lookup("#account");
        Label oc = (Label) root.lookup("#oc");
        Label other = (Label) root.lookup("#other");

        // действие при клике
        allGroups.setOnMouseClicked(e -> handler.filterByGroups(stage, allGroups.getText()));
        general.setOnMouseClicked(e -> handler.filterByGroups(stage, general.getText()));
        network.setOnMouseClicked(e -> handler.filterByGroups(stage, network.getText()));
        internet.setOnMouseClicked(e -> handler.filterByGroups(stage, internet.getText()));
        mail.setOnMouseClicked(e -> handler.filterByGroups(stage, mail.getText()));
        account.setOnMouseClicked(e -> handler.filterByGroups(stage, account.getText()));
        other.setOnMouseClicked(e -> handler.filterByGroups(stage, other.getText()));
        oc.setOnMouseClicked(e -> handler.filterByGroups(stage, oc.getText()));
    }

    private void setupContextMenu(Stage stage) {
        Parent root = stage.getScene().getRoot();
        TableView<PasswordEntre> table = (TableView<PasswordEntre>) root.lookup("#table");
        // Создаем контекстное меню
        ContextMenu contextMenu = new ContextMenu();

        // Пункт "Получить пароль"
        MenuItem getPassword = new MenuItem("Получить пароль");
        getPassword.setOnAction(event -> {
            handler.getPassword(table.getSelectionModel().getSelectedItem());
            showNotification("Получение пароля", "Пароль успешно скопирован в буфер обмена.\n Через минуту он удалиться!");
        });

        // Пункт "Добавить запись"
        MenuItem addItem = new MenuItem("Добавить запись");
        addItem.setOnAction(event -> handler.openRecordWindow(stage));

        // Пункт "Удалить запись"
        MenuItem deleteItem = new MenuItem("Удалить запись");
        deleteItem.setOnAction(event -> handler.deletePasswordEntre(table.getSelectionModel().getSelectedItem()));

        // Добавляем пункты в меню
        contextMenu.getItems().addAll(getPassword, addItem, deleteItem);

        // Показываем меню при правом клике
        table.setRowFactory(tv -> {
            TableRow<PasswordEntre> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });

        table.setContextMenu(contextMenu);
    }

    private void showNotification(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void createElementsMenuBar(MenuBar menuBar, Stage stage) {
        // Элементы строки меню
        Menu file = new Menu("Файл");
        Menu record = new Menu("Запись");
        Menu service = new Menu("Сервис");

        // Элементы каждого из меню
        MenuItem parameterDatabase = new MenuItem("Текущие параметры");
        parameterDatabase.setId("parameterDatabase");
        MenuItem editMasterPassword = new MenuItem("Изменить мастер-пароль");
        editMasterPassword.setId("editMasterPassword");
        MenuItem exitItemMenu = new MenuItem("Выход");
        exitItemMenu.setId("exitItemMenu");
        MenuItem addRecord = new MenuItem("Добавить записи");
        addRecord.setId("addRecord");
        MenuItem generator = new MenuItem("Генератор");
        generator.setId("generator");

        editMasterPassword.setOnAction(event -> handler.editMasterPassword(stage));
        parameterDatabase.setOnAction(event -> handler.getCurrentParameters(stage));
        generator.setOnAction(event -> handler.openGeneratorPasswordWindow(stage));
        exitItemMenu.setOnAction(event -> handler.exitWindow(stage));
        addRecord.setOnAction(event -> handler.openRecordWindow(stage));

        // Добавление элементов в каждое меню
        file.getItems().addAll(
                parameterDatabase,
                editMasterPassword,
                new SeparatorMenuItem(),
                exitItemMenu
        );

        record.getItems().addAll(
                addRecord
        );
        service.getItems().addAll(
                generator
        );
        menuBar.getMenus().addAll(
                file,
                record,
                service
        );
    }

    private void setupHandler(Stage stage) {
        handler.startWatch(stage);
        stage.setOnCloseRequest(event -> handler.exitWindow(stage));
    }

    private TableColumn<PasswordEntre, String> createPasswordColumn(String nameColumn, String field) {
        TableColumn<PasswordEntre, String> column = createColumn(nameColumn, field);

        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("############");
                }
            }
        });
        return column;
    }

    /**
     * @param nameColumn заголовок колонки
     * @param field      название поля класса, за которым необходимо закрепить колонку таблицы
     * @return Возвращает колонку для создания таблицы
     */
    private TableColumn<PasswordEntre, String> createColumn(String nameColumn, String field) {
        TableColumn<PasswordEntre, String> column = new TableColumn<>();
        column.setCellValueFactory(new PropertyValueFactory<>(field));

        Label hader = new Label(nameColumn);
        hader.setAlignment(Pos.CENTER_LEFT);
        hader.setMaxWidth(Double.MAX_VALUE);
        column.setGraphic(hader);
        column.setPrefWidth(COLUMN_WIDTH);

        return column;
    }
}
