package com.ural.gui.windows.generator.tabs;

import com.ural.gui.core.TabView;
import com.ural.gui.windows.generator.GeneratorHandler;
import com.ural.manager.model.PasswordConfiguration;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsTabView implements TabView {
    private static final int CHECKBOX_WIDTH = 190;
    private static final int CHECKBOX_WIDTH_210 = 210;
    private static final int SPACING_20 = 20;
    private static final int SPACING_10 = 10;
    private static final Insets PADDING = new Insets(10, 15, 10, 15);

    private final GeneratorHandler handler;
    private final PasswordConfiguration.Builder configuration;

    public SettingsTabView(GeneratorHandler handler, PasswordConfiguration.Builder configuration) {
        this.handler = handler;
        this.configuration = configuration;
    }

    @Override
    public Tab createTab(Stage stage) {
        Tab setting = new Tab("Настройки");

        // Поле для ввода количества символов в пароле
        Label countSymbols = new Label("Количество символов в пароле:");
        countSymbols.setPrefWidth(CHECKBOX_WIDTH);
        Spinner<Integer> spinner = handler.createSpinner(5, 20, 1, 100);
        spinner.setId("spinner");

        // checkBox для заглавных букв
        CheckBox upperCaseSymbols = createCheckBox("Заглавные буквы (A, B, C, ...)", CHECKBOX_WIDTH);
        upperCaseSymbols.setId("upperCaseSymbols");
        // checkBox для строчных букв
        CheckBox lowerCaseSymbols = createCheckBox("Строчные буквы (a, b, c, ...)", CHECKBOX_WIDTH);
        lowerCaseSymbols.setId("lowerCaseSymbols");
        // checkBox цифры
        CheckBox digits = createCheckBox("Цифры (0, 1, 2, ...)", CHECKBOX_WIDTH);
        digits.setId("digits");
        // checkBox минус
        CheckBox minus = createCheckBox("Минус ( - )", CHECKBOX_WIDTH);
        minus.setId("minus");
        // checkBox подчеркивание
        CheckBox underscore = createCheckBox("Символ подчеркивания ( _ )", CHECKBOX_WIDTH);
        underscore.setId("underscore");
        // checkBox пробел
        CheckBox space = createCheckBox("Пробельный символ ( )", CHECKBOX_WIDTH);
        space.setId("space");
        // checkBox особые символы
        CheckBox specialSymbols = createCheckBox("Специальные символы (!, @, #, ...)", CHECKBOX_WIDTH_210);
        specialSymbols.setId("specialSymbols");
        // checkBox скобки
        CheckBox staples = createCheckBox("Скобки ({, }, [, ], ...)", CHECKBOX_WIDTH);
        staples.setId("staples");


        VBox checkBoxContainerLeft = new VBox();
        checkBoxContainerLeft.setSpacing(SPACING_10);
        checkBoxContainerLeft.getChildren().addAll(
                upperCaseSymbols,
                lowerCaseSymbols,
                digits,
                minus
        );

        VBox checkBoxContainerRight = new VBox();
        checkBoxContainerRight.setSpacing(SPACING_10);
        checkBoxContainerRight.getChildren().addAll(
                underscore,
                space,
                specialSymbols,
                staples
        );

        HBox checkBoxMainContainer = new HBox();
        checkBoxMainContainer.setSpacing(SPACING_20);
        checkBoxMainContainer.getChildren().addAll(
                checkBoxContainerLeft,
                checkBoxContainerRight
        );

        HBox countSymbolsContainer = new HBox();
        countSymbolsContainer.setSpacing(SPACING_20);
        countSymbolsContainer.getChildren().addAll(
                countSymbols,
                spinner
        );

        setupBindings(upperCaseSymbols, lowerCaseSymbols, digits, minus, underscore, space, specialSymbols, staples, spinner);

        Label hader = new Label();
        // Главный контейнер вкладки
        VBox content = new VBox();

        content.setSpacing(SPACING_10);
        content.setPadding(PADDING);
        content.getChildren().addAll(
                createHader(hader),
                new Separator(),
                countSymbolsContainer,
                checkBoxMainContainer
        );

        setting.setClosable(false);
        setting.setContent(content);
        return setting;
    }

    private Label createHader(Label label) {
        label.setText("Параметры генерации пароля");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold");
        return label;
    }

    private CheckBox createCheckBox(String text, int width) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setPrefWidth(width);
        return checkBox;
    }

    private void setupBindings(
            CheckBox upperCaseSymbols, CheckBox lowerCaseSymbols, CheckBox digits,
            CheckBox minus, CheckBox underscore, CheckBox space,
            CheckBox specialSymbols, CheckBox staples, Spinner<Integer> spinner) {
        upperCaseSymbols.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addUpperCase(newValue);
        });
        lowerCaseSymbols.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addLowerCase(newValue);
        });
        digits.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addDigits(newValue);
        });
        minus.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addMinus(newValue);
        });
        underscore.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addUnderscore(newValue);
        });
        space.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addSpace(newValue);
        });
        specialSymbols.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addSpecialSymbols(newValue);
        });
        staples.selectedProperty().addListener((obs, oldValue, newValue) -> {
            configuration.addStaples(newValue);
        });

        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue >= 0) {
                configuration.addLength(newValue);
            } else {
                configuration.addLength(oldValue);
            }
        });
    }
}
