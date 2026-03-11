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
        ITestListener.super.onTestSuccess(result);
        logger.info(result.getTestClass() + " test success --> " + result.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        logger.info(result.getTestClass() + " start test --> " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        logger.info(result.getTestClass() + " test skipped --> " + result.getTestName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        logger.info(result.getTestClass() + " test failed --> " + result.getName());
        this.driver = ((AppManager)result.getInstance()).getDriver();
        TakeScreenShot.takeScreenShot((TakesScreenshot) driver);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
        logger.info(result.getTestClass() + " test failed with timeout --> " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        logger.info("Information on starting test: {}", context.getStartDate());
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        logger.info("Information on ending test: {}", context.getEndDate());
    }
}
