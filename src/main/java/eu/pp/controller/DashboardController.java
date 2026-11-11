package eu.pp.controller;

import eu.pp.DesktopApplication;
import eu.pp.service.DashboardService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.springframework.stereotype.Component;

@Component
public class DashboardController {
    private final DashboardService dashboardService;

    @FXML private Label headingLabel;
    @FXML private Label contentLabel;
    @FXML private ToggleButton themeToggle;

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
        contentLabel.setText("Witaj w AITools! To przykładowy dashboard JavaFX + Spring Boot.");
    }

    @FXML
    private void showTools() {
        contentLabel.setText("Tutaj pojawią się Twoje narzędzia.");
    }

    @FXML
    private void showSettings() {
        contentLabel.setText("Tutaj pojawią się ustawienia aplikacji.");
    }

    @FXML
    private void toggleTheme() {
        DesktopApplication.setDarkTheme(themeToggle.isSelected());
        themeToggle.setText(themeToggle.isSelected() ? "Jasny motyw" : "Ciemny motyw");
    }
}
