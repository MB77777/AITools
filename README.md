# AITools

Java 25 + Spring Boot + JavaFX + FXML + AtlantaFX + ControlsFX + Maven.

## Requirements

- JDK 25
- Maven 3.9+
- Windows, macOS or Linux with a graphical desktop

## Run

```bash
mvn clean javafx:run
```

Open `src/main/resources/fxml/dashboard.fxml` in Gluon Scene Builder to edit the UI visually.

Spring controllers are instantiated through `FXMLLoader#setControllerFactory(context::getBean)`.

Configuration lives in `src/main/resources/application.yaml`.

## Notes

- This is a non-modular application (no `module-info.java`).
- JavaFX native platform artifacts are resolved by Maven.
- The Maven JavaFX plugin is used for development; creating installers with `jpackage` is a separate step.
- The included chart is an empty placeholder for later data binding.
