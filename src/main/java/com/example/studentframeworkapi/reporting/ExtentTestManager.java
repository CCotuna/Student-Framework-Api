package com.example.studentframeworkapi.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {

    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = ExtentManager.getInstance().createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public static synchronized void logInfo(String message) {
        getTest().log(Status.INFO, message);
    }

    public static synchronized void logPass(String message) {
        getTest().log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public static synchronized void logFail(String message) {
        getTest().log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
    }

    public static synchronized void logSkip(String message) {
        getTest().log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.ORANGE));
    }

    public static synchronized void logWarning(String message) {
        getTest().log(Status.WARNING, MarkupHelper.createLabel(message, ExtentColor.YELLOW));
    }

    public static synchronized void endTest() {
        ExtentManager.getInstance().removeTest(getTest());
    }
}
