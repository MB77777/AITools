package eu.pp.aitools.controller;

import eu.pp.aitools.DesktopApplication;
import eu.pp.aitools.service.DashboardService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class DashboardController {
    private final DashboardService dashboardService;

    @FXML private Label headingLabel;
    @FXML private Label contentLabel;
    @FXML private ToggleButton themeToggle;
    @FXML private VBox dashboardView;
    @FXML private VBox fileToolView;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @FXML
    private void initialize() {
        headingLabel.setText(dashboardService.welcomeMessage());
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        setActiveView(dashboardView);
        headingLabel.setText("AITools Dashboard");
        contentLabel.setText("Welcome to AITools! This is a sample JavaFX and Spring Boot dashboard.");
    }

    @FXML
    private void showTools() {
        setActiveView(dashboardView);
        headingLabel.setText("Tools");
        contentLabel.setText("Your tools will appear here.");
    }

    @FXML
    private void showSettings() {
        setActiveView(dashboardView);
        headingLabel.setText("Settings");
        contentLabel.setText("Application settings will appear here.");
    }

    @FXML
    private void showFileTool() {
        setActiveView(fileToolView);
        headingLabel.setText("File Tool");
    }

    @FXML
    private void toggleTheme() {
        DesktopApplication.setDarkTheme(themeToggle.isSelected());
        themeToggle.setText(themeToggle.isSelected() ? "Light theme" : "Dark theme");
    }

    private void setActiveView(VBox activeView) {
        dashboardView.setVisible(activeView == dashboardView);
        dashboardView.setManaged(activeView == dashboardView);
        fileToolView.setVisible(activeView == fileToolView);
        fileToolView.setManaged(activeView == fileToolView);
    }
}
