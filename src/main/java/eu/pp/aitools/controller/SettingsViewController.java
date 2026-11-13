package eu.pp.aitools.controller;

import eu.pp.aitools.service.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class SettingsViewController {
    private final SettingsService settingsService;

    @FXML private Label descriptionLabel;

    public SettingsViewController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @FXML
    private void initialize() {
        descriptionLabel.setText(settingsService.description());
    }
}
