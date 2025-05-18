package com.ural.gui.windows.generator;

import com.ural.gui.core.TabView;
import com.ural.gui.core.Window;
import com.ural.gui.windows.generator.tabs.AdvancedTabView;
import com.ural.gui.windows.generator.tabs.SettingsTabView;
import com.ural.manager.model.PasswordConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GeneratorWindow implements Window {
    private static final int HEIGHT = 280;
    private static final int WIDTH = 450;


    private final GeneratorHandler handler;
    private final PasswordConfiguration.Builder configuration;

    public GeneratorWindow() {
        this.handler = new GeneratorHandler();
        this.configuration = new PasswordConfiguration.Builder();
    }

    @Override
    public void createWindow(Stage stage) {
        Stage generatorStage = new Stage();
        generatorStage.setTitle("Генерирование пароля");

        // блокировка окна, на котором было вызвано данное окно
        generatorStage.initModality(Modality.WINDOW_MODAL);
        generatorStage.initOwner(stage);

        // Кнопки внизу окна
        Button exitButton = new Button("Закрыть");
        Button okButton = new Button("Сгенерировать");
        HBox buttonContainer = createButtonContainer(exitButton, okButton);

        // Создание вкладки "Настройки"
        TabView settingsTab = new SettingsTabView(handler, configuration);
        // Создание вкладки "Дополнительно"
        TabView advancedTab = new AdvancedTabView(handler);
        // Создание вкладки "Создание"
//        TabView passwordsTabView = new GeneratedPasswordsTabView();

        // Создание общего контейнера для вкладок
        TabPane tabPane = new TabPane();

        tabPane.getTabs().addAll(
                settingsTab.createTab(stage),
                advancedTab.createTab(stage)
//                passwordsTabView.createTab(stage)
        );

        // установка привязок
        setHandler(generatorStage, exitButton);

        // Основной контейнер окна
        VBox root = new VBox();

        root.getChildren().addAll(
                tabPane,
                createSeparator(),
                buttonContainer
        );

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        generatorStage.setScene(scene);
        generatorStage.setResizable(false);
        generatorStage.show();
    }

    // Создание сепаратора между кнопкой снизу
    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 15, 0, 15));
        return separator;
    }

    // создание контейнера для главных кнопок окна
    private HBox createButtonContainer(Button exitButton, Button okButton) {
        HBox container = new HBox(okButton, exitButton);
        container.setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(new Insets(10, 15, 10, 15));
        container.setSpacing(10);
        return container;
    }

    private void setHandler(Stage generatorStage, Button exitButton) {
        exitButton.setOnAction(event -> handler.closingEvent(generatorStage));
    }
}
