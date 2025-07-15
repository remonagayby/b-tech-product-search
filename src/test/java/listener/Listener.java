package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import static base.BaseClass.*;

public class Listener implements ITestListener {

    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static boolean reportInitialized = false;

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.FAIL, "Test Failed: " + result.getName());
        test.log(Status.FAIL, result.getThrowable());

        try {
            String screenshotPath = captureScreenshot(result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            test.warning("Screenshot failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        synchronized (Listener.class) {
            if (!reportInitialized) {
                initializeReport(context);
                reportInitialized = true;
            }
        }
    }

    private void initializeReport(ITestContext context) {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/automation-report.html");
        extentReports = new ExtentReports();

        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName(context.getSuite().getName());
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimelineEnabled(true);

        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    @Override
    public void onFinish(ITestContext context) {
        synchronized (Listener.class) {
            if (reportInitialized && extentReports != null) {
                extentReports.flush();
                try {
                    File reportFile = new File(System.getProperty("user.dir") + "/reports/automation-report.html");
                    Desktop.getDesktop().browse(reportFile.toURI());
                } catch (IOException e) {
                    System.err.println("Error opening report: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}