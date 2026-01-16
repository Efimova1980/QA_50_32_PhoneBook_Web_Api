package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        setDriver(driver);
        driver.get("https://telranedu.web.app/home");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    //pom - page object model

    //searches element here while using clickBtnlogin
    @FindBy(xpath = "//a[text() = 'LOGIN']")
    WebElement btnlogin;

    public void clickBtnlogin(){
        btnlogin.click();
    }


}
