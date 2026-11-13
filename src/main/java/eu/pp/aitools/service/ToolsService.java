package eu.pp.aitools.service;

import org.springframework.stereotype.Service;

@Service
public class ToolsService {
    private Runnable fileToolNavigator;

    public String pageTitle() {
        return "Tools";
    }

    public String description() {
        return "Your tools will appear here.";
    }

    public void setFileToolNavigator(Runnable fileToolNavigator) {
        this.fileToolNavigator = fileToolNavigator;
    }

    public void openFileTool() {
        if (fileToolNavigator != null) {
            fileToolNavigator.run();
        }
    }
}
