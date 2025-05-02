package com.ural.gui.windows.record;

import com.ural.gui.windows.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecordWindow implements Window {
    private static final EventHandlerRecordWindow handler = new EventHandlerRecordWindow();

    @Override
    public void createWindow(Stage owner) {
        Stage recordStage = new Stage();
        recordStage.setTitle("Добавление записи/пароля");

        // блокировка окна, на котором было вызвано данное окно
        recordStage.initModality(Modality.WINDOW_MODAL);
        recordStage.initOwner(owner);

        // Кнопки внизу окна
        Button addRecordButton = new Button("Добавить");
        addRecordButton.setDisable(true);
        Button exitButton = new Button("Закрыть");
        HBox buttonsContainer = new HBox(10, addRecordButton, exitButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setPadding(new Insets(10, 15, 10, 15));
        addRecordButton.setOnAction(event -> handler.addRecordButton(recordStage));
        exitButton.setOnAction(event -> handler.exitWindow(recordStage));

        // Нижний разделитель
        Separator separator = new Separator();

        // Главный контейнер окна
        VBox root = new VBox();

        root.setPadding(new Insets(10, 15, 10, 15));
        root.getChildren().addAll(
                separator,
                buttonsContainer
        );

        Scene scene = new Scene(root, 350, 400);
        recordStage.setResizable(false);
        recordStage.setScene(scene);
        recordStage.show();
    }
}
