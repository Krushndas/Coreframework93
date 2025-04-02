package com.CME.utils;

import com.CME.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

import static com.CME.utils.ExtentManager.logInfo;

public class Common extends BaseTest {

    public static String captureScreenshot(String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + ".png";
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }

    /**
     * To verify actual result given as True/False
     * If result found false, then it will message replace "is " with "is not " to log in the report
     */
    public static void assertionTrue(boolean condition, String message){
        Assert.assertTrue(condition, message.replace("is ", "is not "));
        logInfo(message);
    }

    /**
     * To verify actual result given as True/False
     * If result found True, then it will message replace "is not" with "is " to log in the report
     */
    public static void assertionFalse(boolean condition, String message){
        Assert.assertFalse(condition, message.replace("is not", "is"));
        logInfo(message);
    }

    /**
     * To verify actual result given as Equality with True/False
     * If result found false, then it will message replace "is " with "is not " to log in the report
     */
    public static void assertionEquals(Object expected, Object actual, String message){
        Assert.assertEquals(expected, actual, message.replace("is ", "is not "));
        logInfo(message.replace("is ", "is not "));
    }
}
