package com.core_automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import lombok.Getter;

import static com.core_automation.base.BaseTest.fs;

public class ReportManager {
    private static ExtentReports extent;
    @Getter
    private static ExtentTest test;

    public static void initReports() {
        ExtentSparkReporter spark = new ExtentSparkReporter("reports"+fs+"index.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void flushReports() {
        extent.flush();
    }
}
