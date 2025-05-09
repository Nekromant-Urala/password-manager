package gui.init;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Класс для обработки событий при нажатии на кнопки в окне инициализации
 */
public class EventHandlerInitWindow {

    /**
     * Метод для обработки события кнопки поиска файла/поставщика ключа
     *
     * @param owner
     * @param keyFileField
     */
    static void browseButtonHandler(Stage owner, TextField keyFileField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите ключевой файл");

        // Установка фильтров для файлов (опционально)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Текстовый документ (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(owner);
        if (selectedFile != null) {
            keyFileField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Метод для обработки события при нажатии кнопки ОК.
     * Запуская проверку мастер-пароля.
     *
     * @param owner
     * @param masterPassField
     */
    static void okButtonHandler(Stage owner, PasswordField masterPassField) {
        // Проверка пароля и переход к главному окну
        if (!masterPassField.getText().isEmpty()) {
            System.out.println("Пароль принят!");
            // Здесь можно открыть главное окно
            // new MainWindow().start(new Stage());
            owner.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не введён мастер-пароль");
            alert.showAndWait();
        }
    }

    /**
     * Метод для обработки события кнопки видимости пароля.
     * Обрабатывает событие скрытие пароля.
     *
     * @param showHideButton
     * @param masterPassField
     * @param visiblePasswordField
     */
    static void showHideButtonHandler(Button showHideButton, PasswordField masterPassField, TextField visiblePasswordField) {
        if (showHideButton.getText().equals("Показать")) {
            // Показываем пароль
            visiblePasswordField.setText(masterPassField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            masterPassField.setVisible(false);
            masterPassField.setManaged(false);
            showHideButton.setText("Скрыть");
        } else {
            // Скрываем пароль
            masterPassField.setText(visiblePasswordField.getText());
            masterPassField.setVisible(true);
            masterPassField.setManaged(true);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            showHideButton.setText("Показать");
        }
    }

    /**
     * Метод для обработки события выхода из окна инициализации.
     *
     * @param owner
     */
    static void cancelButtonHandler(Stage owner) {
        owner.close();
    }
}
