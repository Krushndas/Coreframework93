package com.CME.utils;

import com.CME.base.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BaseTest implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.createTest(result.getMethod().getMethodName());
        ExtentManager.logInfo("Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.logInfo("Test passed: " + result.getMethod().getMethodName());
        ExtentManager.getTest().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.logError("Test failed: " + result.getMethod().getMethodName());
        String screenshotPath = Common.captureScreenshot(result.getMethod().getMethodName());
        ExtentManager.getTest().fail(result.getThrowable());
        ExtentManager.getTest().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.logError("Test skipped: " + result.getMethod().getMethodName());
        ExtentManager.getTest().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}
