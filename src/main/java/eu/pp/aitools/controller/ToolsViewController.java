package eu.pp.aitools.controller;

import eu.pp.aitools.service.ToolsService;
import eu.pp.aitools.service.TestToolsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class ToolsViewController {
    private final ToolsService toolsService;
    private final TestToolsService testToolsService;

    @FXML private Label descriptionLabel;

    public ToolsViewController(ToolsService toolsService, TestToolsService testToolsService) {
        this.toolsService = toolsService;
        this.testToolsService = testToolsService;
    }

    @FXML
    private void initialize() {
        descriptionLabel.setText(toolsService.description());
    }

    @FXML
    private void openFileTool() {
        toolsService.openFileTool();
    }

    @FXML
    private void openTestTools() {
        testToolsService.open();
    }
}
