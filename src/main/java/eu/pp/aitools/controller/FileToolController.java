package eu.pp.aitools.controller;

import eu.pp.aitools.model.DirectorySnapshot;
import eu.pp.aitools.model.FileEntry;
import eu.pp.aitools.service.FileToolService;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Component
public class FileToolController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final FileToolService fileToolService;

    @FXML private Label folderNameLabel;
    @FXML private Label folderPathLabel;
    @FXML private Label filesCountLabel;
    @FXML private Label foldersCountLabel;
    @FXML private Label statusLabel;
    @FXML private Button selectFolderButton;
    @FXML private Button goButton;
    @FXML private ToggleButton originalCleanedButton;
    @FXML private TextField rulesFileField;
    @FXML private CheckBox makeCopyCheckBox;
    @FXML private TextField outputFolderField;
    @FXML private Button outputFolderButton;
    @FXML private TableView<FileEntry> filesTable;
    @FXML private TableColumn<FileEntry, String> nameColumn;
    @FXML private TableColumn<FileEntry, String> modifiedColumn;
    @FXML private TableColumn<FileEntry, Number> sizeColumn;

    private Path selectedDirectory;
    private DirectorySnapshot originalSnapshot;

    public FileToolController(FileToolService fileToolService) {
        this.fileToolService = fileToolService;
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().directory() ? cell.getValue().name() + "/" : cell.getValue().name()));
        modifiedColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().modifiedAt().format(DATE_FORMAT)));
        sizeColumn.setCellValueFactory(cell -> new ReadOnlyLongWrapper(cell.getValue().size()));
        outputFolderField.setText(Paths.get(System.getProperty("user.dir"), "done").toString());
        updateOutputFolderVisibility();
        clearSummary();
    }

    @FXML
    private void browseRulesFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select sorting rules file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown files (*.md)", "*.md"));
        java.io.File selectedFile = chooser.showOpenDialog(filesTable.getScene().getWindow());
        if (selectedFile != null) {
            rulesFileField.setText(selectedFile.toPath().toAbsolutePath().normalize().toString());
        }
    }

    @FXML
    private void toggleMakeCopy() {
        updateOutputFolderVisibility();
        if (!makeCopyCheckBox.isSelected()) {
            showOriginalDirectory();
        }
    }

    @FXML
    private void browseOutputFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select output folder");
        java.io.File selectedFolder = chooser.showDialog(filesTable.getScene().getWindow());
        if (selectedFolder != null) {
            outputFolderField.setText(selectedFolder.toPath().toAbsolutePath().normalize().toString());
        }
    }

    private void updateOutputFolderVisibility() {
        boolean visible = makeCopyCheckBox.isSelected();
        outputFolderField.setVisible(visible);
        outputFolderField.setManaged(visible);
        outputFolderButton.setVisible(visible);
        outputFolderButton.setManaged(visible);
        originalCleanedButton.setVisible(visible);
        originalCleanedButton.setManaged(visible);
    }

    @FXML
    private void browseFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select folder");
        Window owner = filesTable.getScene().getWindow();
        java.io.File selectedFolder = chooser.showDialog(owner);
        if (selectedFolder != null) {
            loadDirectory(selectedFolder.toPath());
        }
    }

    @FXML
    private void go() {
        if (selectedDirectory == null) {
            statusLabel.setText("Select a folder before starting.");
        } else if (rulesFileField.getText().isBlank()) {
            statusLabel.setText("Select a Markdown rules file before starting.");
        } else {
            statusLabel.setText("Ready to process the selected folder.");
        }
    }

    @FXML
    private void toggleOriginalCleaned() {
        if (originalCleanedButton.isSelected()) {
            showCleanedDirectory();
        } else {
            showOriginalDirectory();
        }
    }

    private void loadDirectory(Path directory) {
        try {
            DirectorySnapshot snapshot = fileToolService.inspectDirectory(directory);
            selectedDirectory = directory.toAbsolutePath().normalize();
            originalSnapshot = snapshot;
            originalCleanedButton.setSelected(false);
            originalCleanedButton.setText("Original");
            selectFolderButton.setText("Change");
            goButton.setDisable(false);
            showSnapshot(snapshot, "Folder loaded");
        } catch (RuntimeException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    private void showOriginalDirectory() {
        if (originalSnapshot != null) {
            originalCleanedButton.setText("Original");
            showSnapshot(originalSnapshot, "Original folder loaded");
        }
    }

    private void showCleanedDirectory() {
        try {
            Path outputDirectory = Paths.get(outputFolderField.getText());
            DirectorySnapshot cleanedSnapshot = fileToolService.inspectDirectory(outputDirectory);
            originalCleanedButton.setText("Cleaned");
            showSnapshot(cleanedSnapshot, "Cleaned folder loaded");
        } catch (RuntimeException exception) {
            originalCleanedButton.setSelected(false);
            originalCleanedButton.setText("Original");
            statusLabel.setText("Cleaned folder is not available: " + exception.getMessage());
        }
    }

    private void showSnapshot(DirectorySnapshot snapshot, String status) {
            folderNameLabel.setText(snapshot.summary().name());
            folderPathLabel.setText(snapshot.summary().path());
            filesCountLabel.setText(Long.toString(snapshot.summary().filesCount()));
            foldersCountLabel.setText(Long.toString(snapshot.summary().foldersCount()));
            filesTable.getItems().setAll(snapshot.files());
            statusLabel.setText(status);
    }

    private void clearSummary() {
        folderNameLabel.setText("No folder selected");
        folderPathLabel.setText("-");
        filesCountLabel.setText("0");
        foldersCountLabel.setText("0");
        statusLabel.setText("Select a folder to view its contents");
        selectFolderButton.setText("Select");
        goButton.setDisable(true);
        originalCleanedButton.setSelected(false);
        originalCleanedButton.setText("Original");
    }
}
