package com.core_automation.utils;

import com.core_automation.base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestDataUtil extends BaseTest {

    private static JsonNode rootNode;

    public TestDataUtil(String testDataFile){
        try {
            File file = new File(testDataFile);
            ObjectMapper objectMapper = new ObjectMapper();
            rootNode = objectMapper.readTree(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load testData.json file.");
        }
    }

    /**
     * Get a value from the JSON file using a key path.
     * For nested keys, use dot notation (e.g., "users.admin.username").
     */
    public static String getValue(String keyPath) {
        String[] keys = keyPath.split("\\.");
        JsonNode currentNode = rootNode;

        for (String key : keys) {
            if (currentNode == null) {
                throw new RuntimeException("Key not found: " + keyPath);
            }
            currentNode = currentNode.get(key);
        }

        if (currentNode != null) {
            // Handle numeric values explicitly
            if (currentNode.isNumber()) {
                return currentNode.numberValue().toString(); // Convert to string properly
            }
            return currentNode.asText();
        }

        return null;
    }

    /**
     * Get a JSON node for complex objects (e.g., users, test cases).
     */
    public static JsonNode getNode(String keyPath) {
        String[] keys = keyPath.split("\\.");
        JsonNode currentNode = rootNode;

        for (String key : keys) {
            if (currentNode == null) {
                throw new RuntimeException("Key not found: " + keyPath);
            }
            currentNode = currentNode.get(key);
        }

        return currentNode;
    }
}
