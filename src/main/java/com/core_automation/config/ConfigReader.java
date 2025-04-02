package com.core_automation.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigReader {

    private static Map<String, Object> config;

    /**
     * Loads the configuration from the specified YAML file path.
     *
     * @param yamlFilePath Path to the YAML configuration file.
     */
    public static void setConfig(String yamlFilePath) {
        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            config = yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a value from the loaded configuration using a dot-separated key.
     *
     * @param key The dot-separated key to retrieve the value.
     * @return The value from the configuration.
     */
    public static Object get(String key) {
        if (config == null) {
            throw new IllegalStateException("Configuration not loaded. Call setConfig() first.");
        }

        String[] keys = key.split("\\.");
        Object value = config;
        for (String k : keys) {
            if (value instanceof Map) {
                value = ((Map<String, Object>) value).get(k);
            } else {
                throw new RuntimeException("Invalid configuration key: " + key);
            }
        }
        return value;
    }

    public static String getString(String key) {
        return get(key).toString();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key).toString());
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key).toString());
    }
}
