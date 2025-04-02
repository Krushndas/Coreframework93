package com.CME.utils;

import com.CME.base.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.CME.utils.ExtentManager.logInfo;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;

public class TestUtils extends BaseTest {

    public static WebElement findElement(By loc) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(loc)));
    }

    public static List<WebElement> findElements(By loc) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loc));
    }

    public static WebElement findElement(WebElement loc) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(loc)));
    }

    public static List<WebElement> findElements(WebElement loc) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(loc));
    }

    public static By getLocator(String locatorKey, Object... parms) {
        return LocatorUtil.getLocator(locatorKey, parms);
    }

    public static By getMobileLocator(String locatorKey, Object... parms) {
        return LocatorUtil.getMobileLocator(locatorKey, parms);
    }

    public static void enterValue(WebElement loc, String value) {
        WebElement element = waitForElement(loc);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Thread.sleep(500);
            js.executeScript("arguments[0].value='';", element);
            Thread.sleep(500);
            for (char c : value.toCharArray()) {
                element.sendKeys(Character.toString(c));
                Thread.sleep(30); // Reduced delay for better performance
            }
            logInfo("Entered: " + value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void enterValue(By loc, String value) {
        WebElement element = waitForElement(loc);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Thread.sleep(500);
            js.executeScript("arguments[0].value='';", element);
            Thread.sleep(500);
            for (char c : value.toCharArray()) {
                element.sendKeys(Character.toString(c));
                Thread.sleep(30); // Reduced delay for better performance
            }
            logInfo("Entered: " + value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void selectDropdownValue(WebElement loc, String value) {
        Select select = new Select(waitForElement(loc));
        select.selectByVisibleText(value);
        logInfo("Selected: " + value);
    }

    public static void clickElement(By loc) {
        WebElement element = waitForElement(loc);
        try {
            element.click();
            logInfo("Clicked on: " + loc);
        } catch (StaleElementReferenceException e) {
            logInfo("Retrying click due to StaleElementReferenceException: " + loc);
            element = waitForElement(loc);
            element.click();
        }
    }

    public static void clickElement(WebElement loc) {
        WebElement element = waitForElement(loc);
        try {
            element.click();
            logInfo("Clicked on element");
        } catch (StaleElementReferenceException e) {
            logInfo("Retrying click due to StaleElementReferenceException");
            element = waitForElement(loc);
            element.click();
        }
    }

    public static boolean isElementDisplayed(By loc) {
        try {
            return findElement(loc).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static boolean isElementVisible(By loc) {
        try {
            return findElement(loc).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static boolean isElementPresent(By loc) {
        try {
            WebElement element = findElement(loc);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static void swipe(AppiumDriver driver, DirectionEnum direction) {
        Dimension size = driver.manage().window().getSize();
        int startX, endX = 0, startY, endY = 0;
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);

        switch (direction) {
            case LEFT:
                startY = size.height / 2;
                startX = (int) (size.width * 0.8);
                endX = (int) (size.width * 0.2);
                break;
            case RIGHT:
                startY = size.height / 2;
                startX = (int) (size.width * 0.1);
                endX = (int) (size.width * 0.9);
                break;
            case UP:
                startX = size.width / 2;
                startY = (int) (size.height * 0.2);
                endY = (int) (size.height * 0.8);
                break;
            case DOWN:
                startX = size.width / 2;
                startY = (int) (size.height * 0.8);
                endY = (int) (size.height * 0.2);
                break;
            default:
                throw new IllegalStateException("Unexpected swipe direction: " + direction);
        }

        sequence.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(finger.createPointerMove(ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));

        driver.perform(singletonList(sequence));
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public static void scrollToElement(By loc) {
        WebElement element = findElement(loc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private static WebElement waitForElement(By loc) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(loc)));
    }

    private static WebElement waitForElement(WebElement loc) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(loc)));
    }
}
