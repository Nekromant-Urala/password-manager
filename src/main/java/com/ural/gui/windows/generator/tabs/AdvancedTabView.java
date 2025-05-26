package com.ural.gui.windows.generator.tabs;

import com.ural.gui.core.TabView;
import com.ural.gui.windows.generator.GeneratorHandler;
import com.ural.manager.model.PasswordConfiguration;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdvancedTabView implements TabView {
    private final GeneratorHandler handler;
    private final PasswordConfiguration.Builder configuration;

    public AdvancedTabView(GeneratorHandler handler, PasswordConfiguration.Builder configuration) {
        this.handler = handler;
        this.configuration = configuration;
    }

    @Override
    public Tab createTab(Stage stage) {
        Tab advanced = new Tab("Дополнительно");

        // checkBox для запрета повтора символов
        CheckBox prohibitionOfRepetition = new CheckBox("Исключить повторы символов");
        prohibitionOfRepetition.setPadding(new Insets(5, 0, 5, 0));

        // Поле для ввода пользователем значений для того, чтобы их исключить
        Label tittleOnTabAdvanced = new Label("Исключить конкретные символы:");
        TextField symbolsField = new TextField();
        symbolsField.setPromptText("Введите символы, которые хотите исключить");

        Label warning = new Label();
        warning.setText("Предупреждение! Данные опции могут уменьшить сложность пароля.");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Главный контейнер вкладки
        VBox content = new VBox();

        content.setSpacing(10);
        content.setPadding(new Insets(10, 15, 0, 15));
        content.getChildren().addAll(
                prohibitionOfRepetition,
                tittleOnTabAdvanced,
                symbolsField,
                spacer,
                warning
        );

        advanced.setClosable(false);
        advanced.setContent(content);
        return advanced;
    }
}
