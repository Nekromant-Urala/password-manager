package com.ural.gui.windows.db;

import com.ural.gui.core.TabView;
import com.ural.gui.core.Window;
import com.ural.gui.windows.db.tabs.AdvancedTabView;
import com.ural.gui.windows.db.tabs.BasicTabView;
import com.ural.gui.windows.db.tabs.SecurityTabView;
import com.ural.manager.model.SettingsData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreationFileWindow implements Window {
    private static final Insets MARGINS_SEPARATOR = new Insets(10, 15, 0, 15);
    private static final Insets MARGINS_BUTTON = new Insets(5, 15, 5, 15);
    private static final int WINDOW_WIDTH = 410;
    private static final int WINDOW_HEIGHT = 310;
    private static final int SPACING = 10;

    private final SettingsData.Builder settings;
    private final CreationFileHandler handler;

    public CreationFileWindow() {
        this.settings = new SettingsData.Builder();
        this.handler = new CreationFileHandler();
    }

    @Override
    public void createWindow(Stage stage) {
        // Окно создания БД (json-файла)
        Stage createFileStage = new Stage();
        createFileStage.setTitle("Создание базы данных");

        createFileStage.initModality(Modality.WINDOW_MODAL);
        createFileStage.initOwner(stage);

        // Основные поля окна
        TabPane tabPane = new TabPane();
        Button okButton = new Button("Создать");
        okButton.setId("okButton");
        okButton.setDisable(true);
        Button exitButton = new Button("Отмена");
        exitButton.setId("exitButton");

        TabView basicTab = new BasicTabView(settings);
        TabView securityTab = new SecurityTabView(settings, handler);
        TabView advancedTab = new AdvancedTabView(settings);

        // Добавляем вкладки
        tabPane.getTabs().addAll(
                basicTab.createTab(createFileStage),
                securityTab.createTab(createFileStage),
                advancedTab.createTab(createFileStage)
        );

        // Основной контейнер
        VBox root = new VBox();
        root.getChildren().addAll(
                tabPane,
                createSeparate(),
                createButtonContainer(okButton, exitButton)
        );

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        createFileStage.setScene(scene);
        createFileStage.setResizable(false);
        // установка привязок
        setupHandler(createFileStage);
        createFileStage.show();
    }

    // разделитель окна
    private Separator createSeparate() {
        Separator separator = new Separator();
        separator.setPadding(MARGINS_SEPARATOR);
        return separator;
    }

    // контейнер кнопок внизу окна
    private HBox createButtonContainer(Button ok, Button exit) {
        HBox container = new HBox(ok, exit);
        container.setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(MARGINS_BUTTON);
        container.setSpacing(SPACING);
        return container;
    }

    // установка действий для всех кнопок и событий данного окна
    private void setupHandler(Stage stage) {
        Parent root = stage.getScene().getRoot();
        Button okButton = (Button) root.lookup("#okButton");
        Button exitButton = (Button) root.lookup("#exitButton");
        Button showHideButton = (Button) root.lookup("#showHideButton");
        Button browseButton = (Button) root.lookup("#browseButton");

        handler.checkPasswordField(stage);

        okButton.setOnAction(event -> {
            handler.setSettingsData(settings);
            handler.successfulEvent(stage);
        });

        exitButton.setOnAction(event -> {
            handler.closingEvent(stage);
        });

        showHideButton.setOnAction(event -> {
            handler.hideEvent(stage);
        });

        browseButton.setOnAction(event -> {
            handler.searchEvent(stage);
        });
    }
}
