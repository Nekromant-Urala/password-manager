package com.ural.gui.windows.db.tabs;

import com.ural.gui.core.TabView;
import com.ural.gui.windows.db.CreationFileHandler;
import com.ural.manager.model.SettingsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SecurityTabView implements TabView {
    private static final int COMBO_BOX_WIDTH = 120;
    private static final int LABEL_WIDTH = 260;
    private static final Insets MARGINS_SEPARATOR = new Insets(10, 15, 0, 15);
    private static final Insets MARGINS_DESCRIPTION = new Insets(10, 0, 0, 15);
    private static final Insets MARGINS_CONTAINER = new Insets(0, 15, 0, 15);
    private static final Insets MARGINS_BOLD_LABEL = new Insets(5, 0, 0, 15);
    private static final String FUNC_DESCRIPTION = """
            Мастер-пароль преобразуется с помощью функции формирования
            ключа, что добавляет вычисления и усложняет атаки.""";

    private final SettingsData.Builder settings;
    private final CreationFileHandler handler;

    public SecurityTabView(SettingsData.Builder settings, CreationFileHandler handler) {
        this.settings = settings;
        this.handler = handler;
    }

    @Override
    public Tab createTab(Stage stage) {

        // основные поля вкладки
        ComboBox<String> comboBoxAlgorithms = new ComboBox<>();
        ComboBox<String> comboBoxFunctions = new ComboBox<>();
        Spinner<Integer> spinner = handler.createSpinner(1, 600_000, 1_000, COMBO_BOX_WIDTH);

        // установка привязок
        setupBindings(comboBoxAlgorithms, comboBoxFunctions, spinner);

        // главный контейнер второй вкладки (вкладки безопасности)
        VBox content = new VBox();

        content.getChildren().addAll(
                createDescriptionLabel(),
                createSeparator(),
                createBoldLabel("Способ шифрования"),
                createEncryptContainer(comboBoxAlgorithms),
                createSeparator(),
                createBoldLabel("Трансформация ключа"),
                createFunctionsContainer(comboBoxFunctions, spinner)
        );

        Tab securityTab = new Tab("Безопасность");
        securityTab.setClosable(false);
        securityTab.setContent(content);
        return securityTab;
    }

    private Label createDescriptionLabel() {
        Label label = new Label("Здесь можно настроить параметры безопасности на уровне файлов.");
        label.setPadding(MARGINS_DESCRIPTION);
        return label;
    }

    private HBox createEncryptContainer(ComboBox<String> comboBoxAlgorithms) {
        Label labelSecAlg = new Label("Алгоритм шифрования базы данных: ");
        labelSecAlg.setPrefWidth(LABEL_WIDTH);
        ObservableList<String> algorithms = FXCollections.observableArrayList("AES", "ChaCha20", "TwoFish", "3DES");
        comboBoxAlgorithms.setItems(algorithms);
        comboBoxAlgorithms.setValue("AES");
        comboBoxAlgorithms.setPrefWidth(COMBO_BOX_WIDTH);

        HBox encryptContainer = new HBox();
        encryptContainer.setPadding(MARGINS_CONTAINER);
        encryptContainer.getChildren().addAll(
                labelSecAlg,
                comboBoxAlgorithms
        );

        return encryptContainer;
    }

    private Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 13px; -fx-font-weight: bold");
        label.setPadding(MARGINS_BOLD_LABEL);
        return label;
    }

    private HBox createSpinnerContainer(Spinner<Integer> spinner) {
        HBox spinnerContainer = new HBox();
        Label countIteration = new Label("Количество итераций: ");
        countIteration.setPrefWidth(LABEL_WIDTH);

        spinnerContainer.getChildren().addAll(
                countIteration,
                spinner
        );
        return spinnerContainer;
    }

    private HBox createComboBoxFunc(ComboBox<String> comboBoxFunctions) {
        HBox nameFunctionContainer = new HBox();
        Label funcLabel = new Label("Функция формирования ключа: ");
        funcLabel.setPrefWidth(LABEL_WIDTH);
        ObservableList<String> nameFunctions = FXCollections.observableArrayList("PBKDF2", "Argon2");
        comboBoxFunctions.setItems(nameFunctions);
        comboBoxFunctions.setValue("PBKDF2");
        comboBoxFunctions.setPrefWidth(COMBO_BOX_WIDTH);

        nameFunctionContainer.getChildren().addAll(
                funcLabel,
                comboBoxFunctions
        );
        return nameFunctionContainer;
    }

    private VBox createFunctionsContainer(ComboBox<String> comboBoxFunctions, Spinner<Integer> spinner) {
        VBox funcContainer = new VBox();
        Text description = new Text(FUNC_DESCRIPTION);
        funcContainer.setPadding(MARGINS_CONTAINER);
        funcContainer.setSpacing(10);
        funcContainer.getChildren().addAll(
                description,
                createComboBoxFunc(comboBoxFunctions),
                createSpinnerContainer(spinner)
        );
        return funcContainer;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(MARGINS_SEPARATOR);
        return separator;
    }


    // установка привязок
    private void setupBindings(ComboBox<String> encryptAlgorithm, ComboBox<String> keyFunction, Spinner<Integer> iterations) {
        encryptAlgorithm.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                settings.encryptAlgorithm(newValue);
            } else {
                settings.encryptAlgorithm(oldValue);
            }
        });

        keyFunction.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                settings.keyGenerator(newValue);
            } else {
                settings.keyGenerator(oldValue);
            }
        });

        iterations.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue >= 0) {
                settings.iterations(newValue);
            } else {
                settings.iterations(oldValue);
            }
        });
    }
}
