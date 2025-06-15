package com.ural.gui.windows.settings;

import com.ural.gui.core.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindow implements Window {
    private final SettingsHandler handler;

    private static final int WIDTH_LABEL = 150;
    private static final int SPACING_IN_CONTAINER = 10;

    private static final int WINDOW_WIDTH = 420;
    private static final int WINDOW_HEIGHT = 170;

    private static final Insets ROOT_ALIGNMENT = new Insets(10, 15, 10, 15);
    private static final int ROOT_SPACING = 10;

    public SettingsWindow() {
        this.handler = new SettingsHandler();
    }

    @Override
    public void createWindow(Stage stage) {
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Настройки");

        // блокировка экрана, на котором было вызвано данное окно
        settingsStage.initModality(Modality.WINDOW_MODAL);
        settingsStage.initOwner(stage);

        // кнопки внизу окна
        Button exitButton = new Button("Закрыть");
        exitButton.setId("exitButton");

        // создание главного контейнера
        VBox root = new VBox();
        root.setSpacing(ROOT_SPACING);
        root.setPadding(ROOT_ALIGNMENT);
        root.getChildren().addAll(
                createPathFileContainer(),
                createEncryptAlgContainer(),
                createKeyGeneratorContainer(),
                createDescriptionFieldContainer(),
                createSeparator(),
                createButtonContainer(exitButton)
        );

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        settingsStage.setScene(scene);
        settingsStage.setResizable(false);
        // установка привязок
        setupHandler(settingsStage);
        settingsStage.show();
    }

    private Separator createSeparator() {
        return new Separator();
    }

    private Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setPrefWidth(WIDTH_LABEL);
        return label;
    }

    private HBox createButtonContainer(Button button) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_RIGHT);
        container.getChildren().addAll(
                button
        );
        return container;
    }

    private Label createLabelParameters(String text) {
        Label label = new Label();
        label.setText(text);
        return label;
    }

    private HBox createDescriptionFieldContainer() {
        String description = handler.getDescriptionDatabase();
        return createContainer("Заметка о файле: ", description);
    }

    private HBox createPathFileContainer() {
        String pathFile = handler.getPathFile();
        return createContainer("Путь до файла: ", pathFile);
    }

    private HBox createEncryptAlgContainer() {
        String encryptAlg = handler.getEncryptAlgorithm();
        return createContainer("Алгоритм шифрования: ", encryptAlg);
    }

    private HBox createKeyGeneratorContainer() {
        String keyGenerator = handler.getKeyGenerator();
        return createContainer("Генератор ключа: ", keyGenerator);
    }

    private HBox createContainer(String text, String text1) {
        HBox container = new HBox();
        Label label = createLabel(text);
        Label label1 = createLabelParameters(text1);
        container.setSpacing(SPACING_IN_CONTAINER);
        HBox.setHgrow(label1, Priority.ALWAYS);
        container.getChildren().addAll(
                label,
                label1
        );
        return container;
    }

    private void setupHandler(Stage stage) {
        Parent root = stage.getScene().getRoot();
        Button exitButton = (Button) root.lookup("#exitButton");
        exitButton.setOnAction(event -> handler.closingEvent(stage));
    }
}
