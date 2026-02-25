package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class WDListener implements WebDriverListener {
    Logger logger = LoggerFactory.getLogger(WDListener.class);

    @Override
    public void afterClick(WebElement element) {
        WebDriverListener.super.afterClick(element);
        logger.info("AfterClick: element --> {}", element.getTagName());
    }

    @Override
    public void beforeClick(WebElement element) {
        WebDriverListener.super.beforeClick(element);
        logger.info("BeforeClick: element --> {}", element.getTagName());
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        WebDriverListener.super.afterGet(driver, url);
        logger.info("AfterGet: page --> {}", url);
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        WebDriverListener.super.beforeGet(driver, url);
        logger.info("BeforeGet: page --> {}", url);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        WebDriverListener.super.afterFindElement(driver, locator, result);
        logger.info("AfterFindElement: found element --> {}, locator --> {}",  result.getTagName(), locator.toString());
    }

    @Override
    public void afterQuit(WebDriver driver) {
        WebDriverListener.super.afterQuit(driver);
        logger.info("Browser closed");
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        WebDriverListener.super.afterSendKeys(element, keysToSend);
        logger.info("AfterSendKeys: Keys --> {}, sent to element --> {}"
                , Arrays.toString(keysToSend)
                , element.getTagName());
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        WebDriverListener.super.afterMaximize(window);
        logger.info("Window maximized --> {}", window.getSize());
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        WebDriverListener.super.onError(target, method, args, e);
        logger.error("Error {} in method --> {}",  e.getClass(), method.getName());
    }
}
