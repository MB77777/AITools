package eu.pp.aitools.controller;

import eu.pp.aitools.DesktopApplication;
import eu.pp.aitools.service.DashboardService;
import eu.pp.aitools.service.FileToolService;
import eu.pp.aitools.service.SettingsService;
import eu.pp.aitools.service.ToolsService;
import eu.pp.aitools.service.TestToolsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class DashboardController {
    private final DashboardService dashboardService;
    private final ToolsService toolsService;
    private final FileToolService fileToolService;
    private final SettingsService settingsService;
    private final TestToolsService testToolsService;

    @FXML private Label headingLabel;
    @FXML private ToggleButton themeToggle;
    @FXML private VBox dashboardView;
    @FXML private VBox toolsView;
    @FXML private VBox fileToolView;
    @FXML private VBox settingsView;
    @FXML private VBox testToolsView;

    public DashboardController(
            DashboardService dashboardService,
            ToolsService toolsService,
            FileToolService fileToolService,
            SettingsService settingsService,
            TestToolsService testToolsService) {
        this.dashboardService = dashboardService;
        this.toolsService = toolsService;
        this.fileToolService = fileToolService;
        this.settingsService = settingsService;
        this.testToolsService = testToolsService;
    }

    @FXML
    private void initialize() {
        toolsService.setFileToolNavigator(this::showFileTool);
        testToolsService.setNavigator(this::showTestTools);
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        setActiveView(dashboardView);
        headingLabel.setText(dashboardService.pageTitle());
    }

    @FXML
    private void showTools() {
        setActiveView(toolsView);
        headingLabel.setText(toolsService.pageTitle());
    }

    @FXML
    private void showSettings() {
        setActiveView(settingsView);
        headingLabel.setText(settingsService.pageTitle());
    }

    @FXML
    private void showFileTool() {
        setActiveView(fileToolView);
        headingLabel.setText(fileToolService.pageTitle());
    }

    @FXML
    private void showTestTools() {
        setActiveView(testToolsView);
        headingLabel.setText(testToolsService.pageTitle());
    }

    @FXML
    private void toggleTheme() {
        DesktopApplication.setDarkTheme(themeToggle.isSelected());
        themeToggle.setText(themeToggle.isSelected() ? "Light theme" : "Dark theme");
    }

    private void setActiveView(VBox activeView) {
        setViewState(dashboardView, activeView == dashboardView);
        setViewState(toolsView, activeView == toolsView);
        setViewState(fileToolView, activeView == fileToolView);
        setViewState(settingsView, activeView == settingsView);
        setViewState(testToolsView, activeView == testToolsView);
    }

    private void setViewState(VBox view, boolean active) {
        view.setVisible(active);
        view.setManaged(active);
    }
}
