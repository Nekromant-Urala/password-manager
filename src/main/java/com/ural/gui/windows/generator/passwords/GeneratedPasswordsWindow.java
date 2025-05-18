package com.ural.gui.windows.generator.passwords;

import com.ural.gui.core.Window;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeneratedPasswordsWindow implements Window {
    private static final int HEIGHT = 350;
    private static final int WIDTH = 450;
    private static final int HEIGHT_TEXT_AREA = 420;
    private static final int WIDTH_TEXT_AREA = 400;
    private static final Insets PADDING = new Insets(10, 15, 0, 15);
    private static final int SPACING = 10;

    public GeneratedPasswordsWindow() {

    }

    @Override
    public void createWindow(Stage stage) {
        Stage generatedPasswordsStage = new Stage();

        // Заголовок вкладки
        Label tittle = new Label("Созданные пароли:");

        // Поле где появятся сгенерированные пароли и будет возможность их отредактировать
        TextArea passwordField = new TextArea();
        passwordField.setPrefSize(HEIGHT_TEXT_AREA, WIDTH_TEXT_AREA);

        // Главный контейнер вкладки
        VBox root = new VBox();

        root.setPadding(PADDING);
        VBox.setVgrow(passwordField, Priority.ALWAYS);
        root.setSpacing(SPACING);
        root.getChildren().addAll(
                tittle,
                new Separator(),
                passwordField
        );

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        generatedPasswordsStage.setScene(scene);
        generatedPasswordsStage.setResizable(false);
        generatedPasswordsStage.show();
    }
}
