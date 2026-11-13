package eu.pp.aitools.controller;

import eu.pp.aitools.service.ToolsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class ToolsViewController {
    private final ToolsService toolsService;

    @FXML private Label descriptionLabel;

    public ToolsViewController(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    @FXML
    private void initialize() {
        descriptionLabel.setText(toolsService.description());
    }

    @FXML
    private void openFileTool() {
        toolsService.openFileTool();
    }
}
