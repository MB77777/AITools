package eu.pp.aitools.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class TestToolsService {
    private final Sinks.Many<String> answerSink = Sinks.many().replay().latest();
    private Runnable navigator;

    public String pageTitle() {
        return "Test Tools";
    }

    public Flux<String> answers() {
        return answerSink.asFlux();
    }

    public void sendPrompt(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            return;
        }
        answerSink.tryEmitNext("Test LLM answer for: " + prompt.trim());
    }

    public void setNavigator(Runnable navigator) {
        this.navigator = navigator;
    }

    public void open() {
        if (navigator != null) {
            navigator.run();
        }
    }
}
