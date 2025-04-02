package com.core_automation.base;

import com.core_automation.config.ServerManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class BaseTest {
    public static Logger log = LoggerFactory.getLogger(BaseTest.class);
    public static WebDriver driver;
    public static Properties prop;
    public static int timestamp = (int) (new Date().getTime()/1000);
    public static WebDriverWait wait;
    public static String fs = File.separator;
    public static String platformName;
    public static AppiumDriver appiumDriver;

    public static void setUp(String platform, String configFilePath) {
        platformName = platform;
        try (InputStream inputStream = new FileInputStream(configFilePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);

            if (platform.equalsIgnoreCase("web")) {
                Map<String, String> webConfig = (Map<String, String>) config.get("web");
                if(webConfig.get("browser").equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                }else {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                }
            } else if (platform.equalsIgnoreCase("android")) {
                new ServerManager().startServer();
                Map<String, Object> androidConfig = (Map<String, Object>) ((Map<String, Object>) config.get("mobile")).get("android");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("platformName", androidConfig.get("platformName"));
                capabilities.setCapability("platformVersion", androidConfig.get("platformVersion"));
                capabilities.setCapability("deviceName", androidConfig.get("deviceName"));
                capabilities.setCapability("app", androidConfig.get("appPath"));
                capabilities.setCapability("appPackage", androidConfig.get("appPackage"));
                capabilities.setCapability("automationName", androidConfig.get("automationName"));
                UiAutomator2Options options = new UiAutomator2Options()
                        .merge(capabilities);
                appiumDriver = new AndroidDriver(new ServerManager().getServer().getUrl(), options);
            } else if (platform.equalsIgnoreCase("ios")) {
                new ServerManager().startServer();
                Map<String, Object> iosConfig = (Map<String, Object>) ((Map<String, Object>) config.get("mobile")).get("ios");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("platformName", iosConfig.get("platformName"));
                capabilities.setCapability("platformVersion", iosConfig.get("platformVersion"));
                capabilities.setCapability("deviceName", iosConfig.get("deviceName"));
                capabilities.setCapability("app", iosConfig.get("appPath"));
                capabilities.setCapability("bundleId", iosConfig.get("bundle"));
                capabilities.setCapability("automationName", iosConfig.get("automationName"));
                XCUITestOptions options = new XCUITestOptions().merge(capabilities);
                appiumDriver = new IOSDriver(new ServerManager().getServer().getUrl(), options);
            } else {
                throw new IllegalArgumentException("Invalid platform: " + platform);
            }
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if(appiumDriver != null) {
            appiumDriver.quit();
            ServerManager serverManager = new ServerManager();
            if (serverManager.getServer() != null) {
                serverManager.getServer().stop();
            }
        }
    }
}
