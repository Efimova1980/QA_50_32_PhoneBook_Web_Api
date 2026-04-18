package utils;

import manager.AppManager;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {
    private WebDriver driver;
    Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    @Override
    public void onTestSuccess(ITestResult result) {
        //ITestListener.super.onTestSuccess(result);
        logger.info("TEST PASSED: {}#{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        //ITestListener.super.onTestStart(result);
        logger.info("TEST STARTED: {}#{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //ITestListener.super.onTestSkipped(result);
        logger.info("TEST SKIPPED: {}#{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //what test class and method was failed
        logger.info("TEST FAILED: {}#{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());

        //if test failed with exception
        if (result.getThrowable() != null) {
            logger.error("Failure reason:", result.getThrowable());
        }

        //all UI tests extends AppManager but API test do not
        if (result.getInstance() instanceof AppManager appManager) {
            this.driver = appManager.getDriver();
        }

        //ChromeDriver implements TakeScreenshot
        if (driver instanceof TakesScreenshot ts) {
            TakeScreenShot.takeScreenShot(ts);
        } else {
            logger.warn("Driver is null or unsupported for screenshots: {}",
                    result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        //ITestListener.super.onTestFailedWithTimeout(result);
        logger.info("TIMEOUT FAILURE: {}#{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());    }

    @Override
    public void onStart(ITestContext context) {
        //ITestListener.super.onStart(context);
        logger.info("Test suite '{}' started at: {}",
                context.getName(),
                context.getStartDate());
    }

    @Override
    public void onFinish(ITestContext context) {
        //ITestListener.super.onFinish(context);
        logger.info("Test suite '{}' finished at: {}",
                context.getName(),
                context.getEndDate());
    }
}
