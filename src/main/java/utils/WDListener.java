package utils;

import org.openqa.selenium.Alert;
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

    //all super methods in selenium are empty and exists only for overriding if necessary
    //they are all default, overriding is not necessary

    @Override
    public void afterAnyAlertCall(Alert alert, Method method, Object[] args, Object result) {
        //alert is a browser dialog window which blokes the page and claims reaction from the user
        //it appears when JS creates it
        //alert may be as message, confirm (yes - accept, cancel - dismiss) or text entering
        //alert lives during a very short time, that is why might be exception during alert.getText()
        /*
        Method (Alert)	    Result (result)
        getText()	        String (alert text)
        accept()	        null
        dismiss()	        null
        sendKeys("text")	null
         */
        //WebDriverListener.super.afterAnyAlertCall(alert, method, args, result); //not necessary
        logger.info("Alert method --> {}, result --> {}", method.getName(), result);
    }

    @Override
    public void afterClick(WebElement element) {
        /*
        Method	                Output
        element.getText()	    "Sign in"   (might be exception if DOM changed, necessary try-catch)
        element.getTagName()	"button"    (might be exception if DOM changed, necessary try-catch)
        element.toString()	    description of locator + driver context
         */
        logger.info("AfterClick: element --> {}", element);
    }

    @Override
    public void beforeClick(WebElement element) {
        logger.info("BeforeClick: element --> {}", element);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        //call after navigation to url
        /*
        Field	                Meaning
        url	                    requested URL (the one passed to driver.get())
        driver.getCurrentUrl()  actual current URL after navigation
        driver.getTitle()	    page title after load
        afterGet	            hook triggered after navigation is completed
         */
        logger.info("AfterGet: url requested --> {}, current url --> {}",
                url, driver.getCurrentUrl());
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("BeforeGet: url --> {}", url);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        //call after find_element_method successes
        /*
        Parameter	        Meaning	                                Safety
        driver	            current WebDriver instance	            safe (not used here)
        locator	            search condition used to find element   safe
        result	            found WebElement (DOM proxy element)	may become stale
        result.getTagName() HTML tag of element (e.g. "button")     may throw StaleElementReferenceException
         */
        logger.info("AfterFindElement: locator --> {}", result);
    }

    @Override
    public void afterQuit(WebDriver driver) {
        logger.info("Browser closed");
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        logger.info("AfterSendKeys: keys --> {}, element --> [{}], value --> {}",
                Arrays.toString(keysToSend),
                element,
                element.getAttribute("value"));
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        logger.info("Window maximized --> {}", window.getSize());
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        logger.error("Error in method {} on target {} with args {}: {}",
                method.getName(), //method which failed
                target, //object which called method
                Arrays.toString(args), //parameters of the failed method
                e.getCause()); //cause of error
    }
}
