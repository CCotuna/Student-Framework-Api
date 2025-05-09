package com.example.studentframeworkapi.testng;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.example.studentframeworkapi.reporting.ExtentManager;
import com.example.studentframeworkapi.reporting.ExtentTestManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("************* TEST SUITE START ***************");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("************* TEST START ***************");
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("************* TEST PASSED ***************");
        ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("************* TEST FAILED ***************");
        ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
        ExtentTestManager.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("************* TEST SKIPPED ***************");
        ExtentTestManager.getTest().log(Status.SKIP, MarkupHelper.createLabel("Test Skipped", ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("************* TEST SUITE FINISHED ***************");
        ExtentManager.getInstance().flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}