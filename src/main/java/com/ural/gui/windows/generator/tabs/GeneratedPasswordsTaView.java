package com.ural.gui.windows.generator.tabs;

import com.ural.gui.core.TabView;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeneratedPasswordsTaView implements TabView {
    private static final int HEIGHT_TEXT_AREA = 420;
    private static final int WIDTH_TEXT_AREA = 400;
    private static final Insets PADDING = new Insets(10, 15, 0, 15);
    private static final int SPACING = 10;

    @Override
    public Tab createTab(Stage stage) {
        Tab generatedPasswordsStage = new Tab("Создание");
        // Заголовок вкладки
        Label tittle = new Label("Созданные пароли:");
        // Поле где появятся сгенерированные пароли и будет возможность их отредактировать
        TextArea passwordField = new TextArea();
        passwordField.setId("generatedPasswords");
        passwordField.setPrefSize(HEIGHT_TEXT_AREA, WIDTH_TEXT_AREA);

        // Главный контейнер вкладки
        VBox content = new VBox();
        content.setPadding(PADDING);
        VBox.setVgrow(passwordField, Priority.ALWAYS);
        content.setSpacing(SPACING);
        content.getChildren().addAll(
                tittle,
                new Separator(),
                passwordField
        );

        generatedPasswordsStage.setClosable(false);
        generatedPasswordsStage.setContent(content);
        return generatedPasswordsStage;
    }
}
