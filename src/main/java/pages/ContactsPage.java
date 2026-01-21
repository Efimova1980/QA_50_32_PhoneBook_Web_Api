package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ContactsPage extends BasePage{
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='ADD']")
    WebElement btnAdd;

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContacts;

    @FindBy(xpath = "//a[text()='ABOUT']")
    WebElement btnAbout;

    @FindBy(xpath = "//a[text()='HOME']")
    WebElement btnHome;

    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;

    public void clickBtnAdd(){
        btnAdd.click();
    }

    public void clickBtnContacts(){
        btnContacts.click();
    }

    public boolean isBtnAddDisplayed(){
        return isElementDisplayed(btnAdd);
    }

    public boolean isBtnContactsDisplayed(){
        return isElementDisplayed(btnContacts);
    }
}
