package eu.pp.aitools.controller;

import eu.pp.aitools.service.TestToolsService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class TestToolsController {
    private final TestToolsService testToolsService;

    @FXML private VBox testToolsRoot;
    @FXML private TextArea promptTextArea;
    @FXML private TextArea answerTextArea;
    @FXML private Button sendButton;

    public TestToolsController(TestToolsService testToolsService) {
        this.testToolsService = testToolsService;
    }

    @FXML
    private void initialize() {
        testToolsRoot.heightProperty().addListener((observable, oldHeight, newHeight) ->
                setPromptHeight(newHeight.doubleValue()));
        setPromptHeight(testToolsRoot.getPrefHeight());
        testToolsService.answers().subscribe(answer ->
                Platform.runLater(() -> answerTextArea.appendText(answer + System.lineSeparator())));
    }

    @FXML
    private void sendPrompt() {
        String prompt = promptTextArea.getText();
        testToolsService.sendPrompt(prompt);
        promptTextArea.clear();
    }

    private void setPromptHeight(double formHeight) {
        if (formHeight > 0) {
            double promptHeight = formHeight * 0.2;
            promptTextArea.setPrefHeight(promptHeight);
            promptTextArea.setMinHeight(promptHeight);
            promptTextArea.setMaxHeight(promptHeight);
        }
    }
}
