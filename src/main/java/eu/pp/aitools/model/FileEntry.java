package eu.pp.aitools.model;

import java.time.LocalDateTime;

public record FileEntry(String name, LocalDateTime modifiedAt, long size, boolean directory) {
}
