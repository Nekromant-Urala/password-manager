package com.ural.gui.windows.db.tabs;

import com.ural.gui.core.TabView;
import com.ural.manager.model.SettingsData;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdvancedTabView implements TabView {
    private static final Insets MARGINS = new Insets(10, 15, 0, 15);
    private static final int DESCRIPTION_WIDTH = 250;
    private static final int DESCRIPTION_HEIGHT = 170;
    private static final int COLUMN_COUNT = 20;
    private static final int ROW_COUNT = 5;
    private static final int SPACING = 10;
    private final SettingsData.Builder settings;

    public AdvancedTabView(SettingsData.Builder settings) {
        this.settings = settings;
    }

    @Override
    public Tab createTab(Stage stage) {
        // Основные поля вкладки
        Label hader = new Label("Добавьте описание для базы данных.");
        TextArea description = new TextArea();
        description.setId("description");

        // установка привязок
        setupBindings(description);

        // Главный контейнер вкладки
        VBox content = new VBox();
        content.setPadding(MARGINS);
        content.setSpacing(SPACING);
        content.getChildren().addAll(
                hader,
                new Separator(),
                createDescriptionField(description)
        );

        Tab advancedTab = new Tab("Дополнительно");
        advancedTab.setClosable(false);
        advancedTab.setContent(content);
        return advancedTab;
    }

    // Поле для ввода текста описания БД
    private TextArea createDescriptionField(TextArea description) {
        description.setPromptText("Описание базы данных");
        description.setPrefSize(DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT);
        description.setPrefColumnCount(COLUMN_COUNT);
        description.setPrefRowCount(ROW_COUNT);
        return description;
    }

    // установка привязок
    private void setupBindings(TextArea descriptionField) {
        descriptionField.textProperty().addListener((obs, oldValue, newValue) -> {
            settings.description(newValue);
        });
    }
}
