package com.example.studentframeworkapi.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.io.File;
import java.time.LocalDate;

public class ExtentManager {

    private static ExtentReports extent;
    private static final LocalDate CURRENT_DATE = LocalDate.now();

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = createExtentReports();
        }
        return extent;
    }

    private static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./test-output/extent-report.html")
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{
                        ViewName.TEST,
                        ViewName.DASHBOARD,
                        ViewName.CATEGORY,
                        ViewName.DEVICE
                })
                .apply();

        try {
            reporter.loadXMLConfig(new File("src/main/resources/extentReportConfig.xml"));
        } catch (Exception e) {
            System.out.println("Error loading extent config: " + e.getMessage());
        }

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Author", System.getProperty("user.name"));
        extent.setSystemInfo("Date", CURRENT_DATE.toString());

        return extent;
    }
}