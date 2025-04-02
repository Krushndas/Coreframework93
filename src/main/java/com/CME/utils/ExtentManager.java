package com.CME.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.CME.base.BaseTest.timestamp;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static final Logger log = LogManager.getLogger(ExtentManager.class);

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/ExtentReport/ExtentReport_"+timestamp+".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("Test Automation Report");
            sparkReporter.config().setDocumentTitle("Automation Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void logInfo(String message) {
        log.info(message);  // Log to console
        getTest().info(message); // Log to Extend Report
    }

    public static void logError(String message) {
        log.error(message);  // Log the error to the console using Log4j
        getTest().fail(message); // Log the error in Extent Report
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
