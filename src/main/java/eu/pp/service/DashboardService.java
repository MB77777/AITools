package eu.pp.service;

import eu.pp.config.AppProperties;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final AppProperties properties;

    public DashboardService(AppProperties properties) {
        this.properties = properties;
    }

    public String welcomeMessage() {
        return properties.welcomeMessage();
    }
}
