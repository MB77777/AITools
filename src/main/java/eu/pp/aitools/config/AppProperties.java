package eu.pp.aitools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aitools")
public record AppProperties(String welcomeMessage) {
}
