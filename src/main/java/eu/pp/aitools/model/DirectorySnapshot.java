package eu.pp.aitools.model;

import java.util.List;

public record DirectorySnapshot(DirectorySummary summary, List<FileEntry> files) {
}
