package eu.pp.aitools.service;

import org.springframework.stereotype.Service;

@Service
public class ToolsService {
    public String pageTitle() {
        return "Tools";
    }

    public String description() {
        return "Your tools will appear here.";
    }
}
