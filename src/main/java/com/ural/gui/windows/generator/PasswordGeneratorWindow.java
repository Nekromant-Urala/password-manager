package com.ural.gui.windows.generator;

import com.ural.gui.windows.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordGeneratorWindow implements Window {
    private static final EventHandlerGeneratorWindow handler = new EventHandlerGeneratorWindow();

    @Override
    public void createWindow(Stage owner) {
        Stage generatorStage = new Stage();
        generatorStage.setTitle("Генерирование пароля");

        // блокировка окна, на котором было вызвано данное окно
        generatorStage.initModality(Modality.WINDOW_MODAL);
        generatorStage.initOwner(owner);

        // Кнопки внизу окна
        Button exitButton = new Button("Закрыть");
        HBox buttonContainer = new HBox(exitButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setPadding(new Insets(10, 15, 10, 15));
        exitButton.setOnAction(event -> handler.exitButtonHandler(generatorStage));

        // Создание вкладки "Настройки"
        Tab settingsTab = createSettingsTab();
        // Создание вкладки "Дополнительно"
        Tab advancedTab = createAdvancedTab();
        // Создание вкладки "Создание"
        Tab createTab = createCreatureTab();

        // Создание общего контейнера для вкладок
        TabPane tabPane = new TabPane();

        tabPane.getTabs().addAll(
                settingsTab,
                advancedTab,
                createTab
        );

        // Основной контейнер окна
        VBox root = new VBox();

        // Создание сепаратора между кнопкой снизу
        Separator separatorWindow = new Separator();
        separatorWindow.setPadding(new Insets(10, 15, 0, 15));

        root.getChildren().addAll(
                tabPane,
                separatorWindow,
                buttonContainer
        );

        Scene scene = new Scene(root, 450, 500);
        generatorStage.setScene(scene);
        generatorStage.setResizable(false);
        generatorStage.show();
    }

    private Tab createCreatureTab() {
        Tab Creature = new Tab("Создание");

        // Заголовок вкладки
        Label tittle = new Label("Созданные пароли:");

        // Поле где появятся сгенерированные пароли и будет возможность их отредактировать
        TextArea passwordField = new TextArea();
        passwordField.setPrefSize(420, 400);

        // Главный контейнер вкладки
        VBox content = new VBox();
        Separator separator = new Separator();

        content.setPadding(new Insets(10, 15, 0, 15));
        VBox.setVgrow(passwordField, Priority.ALWAYS);
        content.setSpacing(10);
        content.getChildren().addAll(
                tittle,
                separator,
                passwordField
        );

        Creature.setClosable(false);
        Creature.setContent(content);
        return Creature;
    }

    private Tab createAdvancedTab() {
        Tab advanced = new Tab("Дополнительно");

        // checkBox для исключения каких-то символов

        // checkBox для запрета повтора символов

        // Поле для ввода пользователем значений для того, чтобы их исключить

        // Главный контейнер вкладки
        VBox content = new VBox();

        content.getChildren().addAll(

        );

        advanced.setClosable(false);
        advanced.setContent(content);
        return advanced;
    }

    private Tab createSettingsTab() {
        Tab setting = new Tab("Настройки");

        // Поле для ввода количества символов в пароле

        // checkBox для заглавных букв

        // checkBox для строчных букв

        // checkBox цифры

        // checkBox минус

        // checkBox подчеркивание

        // checkBox пробел

        // checkBox особые символы

        // checkBox скобки

        // Поле для ввода пользователем символов, которые должны присутствовать

        // Главный контейнер вкладки
        VBox content = new VBox();

        content.getChildren().addAll(

        );

        setting.setClosable(false);
        setting.setContent(content);
        return setting;
    }
}
