package com.core_automation.utils;

import com.core_automation.base.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocatorUtil extends BaseTest {

    private static Properties properties;

    public LocatorUtil(String locatorFile){
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(locatorFile);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load locators.properties file.");
        }
    }

    public static By getMobileLocator(String key, Object... params) {
        String locator = properties.getProperty(key);

        if (locator == null) {
            throw new RuntimeException("Locator not found for key: " + key);
        }

        if (params != null && params.length > 0) {
            locator = String.format(locator, params);
        }

        String[] parts = locator.split("=", 2);
        String type = parts[0];
        String value = parts[1];

        return switch (type.toLowerCase()) {
            case "id" -> AppiumBy.id(value);
            case "name" -> AppiumBy.name(value);
            case "classname" -> AppiumBy.className(value);
            case "tagname" -> AppiumBy.tagName(value);
            case "linktext" -> AppiumBy.linkText(value);
            case "partiallinktext" -> AppiumBy.partialLinkText(value);
            case "css" -> AppiumBy.cssSelector(value);
            case "xpath" -> AppiumBy.xpath(value);
            case "accessibility"-> AppiumBy.accessibilityId(value);
            default -> throw new RuntimeException("Unsupported locator type: " + type);
        };
    }

    public static By getLocator(String key, Object... params) {
        String locator = properties.getProperty(key);

        if (locator == null) {
            throw new RuntimeException("Locator not found for key: " + key);
        }
        if (params != null && params.length > 0) {
            locator = String.format(locator, params);
        }

        String[] parts = locator.split("=", 2);
        String type = parts[0];
        String value = parts[1];

        return switch (type.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "classname" -> By.className(value);
            case "tagname" -> By.tagName(value);
            case "linktext" -> By.linkText(value);
            case "partiallinktext" -> By.partialLinkText(value);
            case "css" -> By.cssSelector(value);
            case "xpath" -> By.xpath(value);
            default -> throw new RuntimeException("Unsupported locator type: " + type);
        };
    }
}
