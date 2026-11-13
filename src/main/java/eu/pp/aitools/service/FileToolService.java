package eu.pp.aitools.service;

import eu.pp.aitools.model.DirectorySnapshot;
import eu.pp.aitools.model.DirectorySummary;
import eu.pp.aitools.model.FileEntry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileToolService {
    public String pageTitle() {
        return "File Tool";
    }

    public DirectorySnapshot inspectDirectory(Path directory) {
        if (directory == null || !Files.isDirectory(directory)) {
            throw new IllegalArgumentException("The selected path is not a directory.");
        }

        try (Stream<Path> entries = Files.list(directory)) {
            List<Path> children = entries.sorted(Comparator
                    .comparing((Path path) -> !Files.isDirectory(path))
                    .thenComparing(path -> path.getFileName().toString(), String.CASE_INSENSITIVE_ORDER))
                    .toList();
            long filesCount = children.stream().filter(Files::isRegularFile).count();
            long foldersCount = children.stream().filter(Files::isDirectory).count();
            List<FileEntry> files = children.stream()
                    .map(this::toFileEntry)
                    .toList();

            DirectorySummary summary = new DirectorySummary(
                    directory.getFileName() == null ? directory.toString() : directory.getFileName().toString(),
                    directory.toAbsolutePath().normalize().toString(),
                    filesCount,
                    foldersCount);
            return new DirectorySnapshot(summary, files);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect the selected directory.", exception);
        }
    }

    private FileEntry toFileEntry(Path file) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
            LocalDateTime modifiedAt = LocalDateTime.ofInstant(
                    attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
            return new FileEntry(file.getFileName().toString(), modifiedAt, attributes.size(), attributes.isDirectory());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read file metadata.", exception);
        }
    }
}
