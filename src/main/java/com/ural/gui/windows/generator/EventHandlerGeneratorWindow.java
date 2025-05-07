package com.ural.gui.windows.generator;

import com.ural.gui.windows.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class EventHandlerGeneratorWindow implements EventHandler {

    void exitButtonHandler(Stage owner) {
        owner.close();
    }

    Spinner<Integer> getIntegerSpinner() {
        Spinner<Integer> spinner = new Spinner<>(1, Integer.MAX_VALUE, 20); // min, max, initial
        spinner.setPrefWidth(100);

        // Установка фабрики значений с правильной логикой инкремента/декремента
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();

        // Обработчик для текстового поля (если пользователь вводит значение вручную)
        spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                // Парсим введенное значение
                int value = Integer.parseInt(newValue);
                // Проверяем границы
                if (value < factory.getMin()) {
                    value = factory.getMin();
                } else if (value > factory.getMax()) {
                    value = factory.getMax();
                }
                factory.setValue(value);
            } catch (NumberFormatException e) {
                // Если введено не число, восстанавливаем предыдущее значение
                if (!newValue.matches("\\d*")) {
                    spinner.getEditor().setText(oldValue);
                }
            }
        });

        // Настройка инкремента/декремента на основе текущего значения
        factory.setAmountToStepBy(1); // Шаг изменения

        spinner.setEditable(true);
        return spinner;
    }
}
