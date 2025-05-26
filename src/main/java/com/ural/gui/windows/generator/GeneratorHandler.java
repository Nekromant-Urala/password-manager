package com.ural.gui.windows.generator;

import com.ural.gui.core.BaseHandlerEvent;
import com.ural.manager.model.PasswordConfiguration;
import com.ural.manager.service.GeneratorService;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

public class GeneratorHandler extends BaseHandlerEvent {
    private PasswordConfiguration configuration;
    private final GeneratorService generatorService;

    public GeneratorHandler() {
        generatorService = new GeneratorService();
    }

    @Override
    public void successfulEvent(Stage stage) {
        Parent root = stage.getScene().getRoot();
        TextArea passwords = (TextArea) root.lookup("#generatedPasswords");
        passwords.setText(generatePasswords());
    }

    private String generatePasswords() {
        generatorService.setConfiguration(configuration);
        StringBuilder builder = new StringBuilder();
        List<String> passwords = generatorService.generate(50);
        passwords.forEach(e -> builder.append(e).append("\n"));
        return builder.toString();
    }

    public void checkCheckBox(Stage stage) {
        Parent root = stage.getScene().getRoot();
        CheckBox upperCaseSymbols = (CheckBox) root.lookup("#upperCaseSymbols");
        CheckBox lowerCaseSymbols = (CheckBox) root.lookup("#lowerCaseSymbols");
        CheckBox digits = (CheckBox) root.lookup("#digits");
        CheckBox minus = (CheckBox) root.lookup("#minus");
        CheckBox underscore = (CheckBox) root.lookup("#underscore");
        CheckBox space = (CheckBox) root.lookup("#space");
        CheckBox specialSymbols = (CheckBox) root.lookup("#specialSymbols");
        CheckBox staples = (CheckBox) root.lookup("#staples");
        Button okButton = (Button) root.lookup("#okButton");

        ChangeListener<Boolean> listener = (obs, oldValue, newValue) -> {
            boolean selected =
                    upperCaseSymbols.isSelected()
                            || lowerCaseSymbols.isSelected()
                            || digits.isSelected()
                            || minus.isSelected()
                            || underscore.isSelected()
                            || space.isSelected()
                            || specialSymbols.isSelected()
                            || staples.isSelected();

            okButton.setDisable(!selected);
        };
        upperCaseSymbols.selectedProperty().addListener(listener);
        lowerCaseSymbols.selectedProperty().addListener(listener);
        digits.selectedProperty().addListener(listener);
        minus.selectedProperty().addListener(listener);
        underscore.selectedProperty().addListener(listener);
        space.selectedProperty().addListener(listener);
        specialSymbols.selectedProperty().addListener(listener);
        staples.selectedProperty().addListener(listener);
    }

    public void setConfiguration(PasswordConfiguration.Builder configuration) {
        this.configuration = configuration.build();
    }
}
