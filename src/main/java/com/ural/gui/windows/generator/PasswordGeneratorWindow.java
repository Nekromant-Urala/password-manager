package com.ural.gui.windows.generator;

import com.ural.gui.windows.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordGeneratorWindow implements Window {
    private static final int CHECKBOX_WIDTH = 190;
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

        Scene scene = new Scene(root, 450, 350);
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

    private Tab createSettingsTab() {
        Tab setting = new Tab("Настройки");

        // Поле для ввода количества символов в пароле
        Label countSymbols = new Label("Количество символов в пароле:");
        countSymbols.setPrefWidth(CHECKBOX_WIDTH);
        Spinner<Integer> spinner = handler.getIntegerSpinner();

        // checkBox для заглавных букв
        CheckBox upperCaseSymbols = new CheckBox("Заглавные буквы (A, B, C, ...)");
        upperCaseSymbols.setPrefWidth(CHECKBOX_WIDTH);

        // checkBox для строчных букв
        CheckBox lowerCaseSymbols = new CheckBox("Строчные буквы (a, b, c, ...)");
        lowerCaseSymbols.setPrefWidth(CHECKBOX_WIDTH);

        // checkBox цифры
        CheckBox digits = new CheckBox("Цифры (0, 1, 2, ...)");
        digits.setPrefWidth(CHECKBOX_WIDTH);

        // checkBox минус
        CheckBox minus = new CheckBox("Минус ( - )");
        minus.setPrefWidth(CHECKBOX_WIDTH);

        // checkBox подчеркивание
        CheckBox underscore = new CheckBox("Символ подчеркивания ( _ )");
        underscore.setPrefWidth(CHECKBOX_WIDTH);

        // checkBox пробел
        CheckBox space = new CheckBox("Пробельный символ ( )");
        space.setPrefWidth(CHECKBOX_WIDTH);

        // checkBox особые символы
        CheckBox specialSymbols = new CheckBox("Специальные символы (!, @, #, ...)");
        specialSymbols.setPrefWidth(210);

        // checkBox скобки
        CheckBox staples = new CheckBox("Скобки ({, }, [, ], ...)");
        staples.setPrefWidth(CHECKBOX_WIDTH);

        // Поле для ввода пользователем символов, которые должны присутствовать
        Label tittleCustomSymbols = new Label("Включить в пароль следующие символы (Необязательное поле):");
        tittleCustomSymbols.setPadding(new Insets(15, 0, 0, 0));
        TextField customSymbols = new TextField();
        customSymbols.setPromptText("Введите символы, которые должны быть в пароле");


        VBox checkBoxContainerLeft = new VBox();
        checkBoxContainerLeft.setSpacing(10);
        checkBoxContainerLeft.getChildren().addAll(
                upperCaseSymbols,
                lowerCaseSymbols,
                digits,
                minus
        );
        VBox checkBoxContainerRight = new VBox();
        checkBoxContainerRight.setSpacing(10);
        checkBoxContainerRight.getChildren().addAll(
                underscore,
                space,
                specialSymbols,
                staples
        );
        HBox checkBoxMainContainer = new HBox();
        checkBoxMainContainer.setSpacing(20);
        checkBoxMainContainer.getChildren().addAll(
                checkBoxContainerLeft,
                checkBoxContainerRight
        );
        HBox countSymbolsContainer = new HBox();
        countSymbolsContainer.setSpacing(20);
        countSymbolsContainer.getChildren().addAll(
                countSymbols,
                spinner
        );

        Label hader = new Label("Параметры генерации пароля");
        hader.setStyle("-fx-font-size: 14px; -fx-font-weight: bold");
        Separator separator = new Separator();
        // Главный контейнер вкладки
        VBox content = new VBox();

        content.setSpacing(10);
        content.setPadding(new Insets(10, 15, 10, 15));
        content.getChildren().addAll(
                hader,
                separator,
                countSymbolsContainer,
                checkBoxMainContainer,
                tittleCustomSymbols,
                customSymbols
        );

        setting.setClosable(false);
        setting.setContent(content);
        return setting;
    }
}
