package eu.pp.aitools.controller;

import eu.pp.aitools.model.DirectorySnapshot;
import eu.pp.aitools.model.FileEntry;
import eu.pp.aitools.service.FileToolService;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
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
    @FXML private TableView<FileEntry> filesTable;
    @FXML private TableColumn<FileEntry, String> nameColumn;
    @FXML private TableColumn<FileEntry, String> modifiedColumn;
    @FXML private TableColumn<FileEntry, Number> sizeColumn;

    public FileToolController(FileToolService fileToolService) {
        this.fileToolService = fileToolService;
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().name()));
        modifiedColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().modifiedAt().format(DATE_FORMAT)));
        sizeColumn.setCellValueFactory(cell -> new ReadOnlyLongWrapper(cell.getValue().size()));
        clearSummary();
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

    private void loadDirectory(Path directory) {
        try {
            DirectorySnapshot snapshot = fileToolService.inspectDirectory(directory);
            folderNameLabel.setText(snapshot.summary().name());
            folderPathLabel.setText(snapshot.summary().path());
            filesCountLabel.setText(Long.toString(snapshot.summary().filesCount()));
            foldersCountLabel.setText(Long.toString(snapshot.summary().foldersCount()));
            filesTable.getItems().setAll(snapshot.files());
            statusLabel.setText("Folder loaded");
        } catch (RuntimeException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    private void clearSummary() {
        folderNameLabel.setText("No folder selected");
        folderPathLabel.setText("-");
        filesCountLabel.setText("0");
        foldersCountLabel.setText("0");
        statusLabel.setText("Select a folder to view its contents");
    }
}
