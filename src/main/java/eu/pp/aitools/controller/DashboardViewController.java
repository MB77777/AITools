package eu.pp.aitools.controller;

import eu.pp.aitools.service.DashboardService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class DashboardViewController {
    private final DashboardService dashboardService;

    @FXML private Label welcomeLabel;

    public DashboardViewController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @FXML
    private void initialize() {
        welcomeLabel.setText(dashboardService.welcomeMessage());
    }
}
