package gui.create;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Класс для обработки событий при нажатии на кнопки в окне создания базы данных
 */
public class EventHandlerDBWindow {

    /**
     * Метод для обработки события кнопки поиска директории для хранения файла
     *
     * @param owner
     * @param keyFileField
     */
    static void browseButtonHandler(Stage owner, TextField keyFileField) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите ключевую директорию");

        File path = directoryChooser.showDialog(owner);

        if (path != null) {
            keyFileField.setText(path.getAbsolutePath());
        }
    }

    static void showHideButtonHandler(Button showHideButton, PasswordField masterPassField, TextField visiblePasswordField, PasswordField passwordConfirmField) {
        if (showHideButton.getText().equals("Показать")) {
            // Показываем пароль
            visiblePasswordField.setText(masterPassField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordConfirmField.setDisable(true);
            masterPassField.setVisible(false);
            masterPassField.setManaged(false);
            showHideButton.setText("Скрыть");
        } else {
            // Скрываем пароль
            masterPassField.setText(visiblePasswordField.getText());
            masterPassField.setVisible(true);
            masterPassField.setManaged(true);
            passwordConfirmField.setDisable(false);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            showHideButton.setText("Показать");
        }
    }

}
