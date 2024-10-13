package com.bookstore.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.bookstore.factory.Factory.captureScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExtentConfig implements ITestListener {

    ExtentSparkReporter extentSparkReporter;
    ExtentReports extentReports;
    String currDir = System.getProperty("user.dir");
    String reportDir;
    private final String CONFIG_FILE_NAME = "Config.xml";
    private final String OUTPUT_FILE_NAME = "index.html";
    ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private ExtentReports extent = reportInit();
    String extentFilePath;

    private ExtentReports reportInit() {
        extentFilePath = createReportingDirectory().getAbsolutePath();
        extentSparkReporter = new ExtentSparkReporter(extentFilePath + File.separator + OUTPUT_FILE_NAME);
        try {
            extentSparkReporter.loadXMLConfig(currDir + "/ExtentConfigXml/" + CONFIG_FILE_NAME);
            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);
            extentReports.setSystemInfo("Host", SystemUtils.getHostName());
            extentReports.setSystemInfo("OS Info", SystemUtils.OS_NAME);
            extentReports.setSystemInfo("OS Arch", SystemUtils.OS_ARCH);
            extentReports.setSystemInfo("OS Version", SystemUtils.OS_VERSION);
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.getMessage());
        }
        return extentReports;
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Execution started at: " + readTime());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Execution finished at: " + readTime());
        extentReports.flush();
        extentTestThreadLocal.remove();
        try {
            FileUtils.copyFile(new File("./Reports/report.log"), new File(extentFilePath + "/report.log"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        test.assignCategory(result.getTestContext().getSuite().getName());
        extentTestThreadLocal.set(test);
        extentTestThreadLocal.get().getModel().setStartTime(readTime());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " is passed successfully");
        extentTestThreadLocal.get().pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        extentTestThreadLocal.get().getModel().setEndTime(readTime());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " is failed!!");
        extentTestThreadLocal.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        extentTestThreadLocal.get().getModel().setEndTime(readTime());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " is skipped");
        extentTestThreadLocal.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        extentTestThreadLocal.get().getModel().setEndTime(readTime());
    }

    public File createReportingDirectory() {
        File reportF, reportSubF;
        reportDir = currDir + "/Reports/";
        reportF = new File(reportDir);
        boolean dirExist = reportF.mkdir();
        if (dirExist) {
            System.out.println("Reporting directory created successfully");
            System.out.println("Now creating sub directory");
            reportSubF = new File(reportDir + "/Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")));
            reportSubF.mkdir();

        } else {
            System.out.println("Reporting directory is already present");
            System.out.println("Now creating sub directory");
            reportSubF = new File(reportDir + "/Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")));
            reportSubF.mkdir();
        }
        return reportSubF;
    }

    public Date readTime() {
        return new Date(System.currentTimeMillis());
    }

}
