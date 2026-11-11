package eu.pp.aitools.service;

import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    public String pageTitle() {
        return "Settings";
    }

    public String description() {
        return "Application settings will appear here.";
    }
}
