package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ContactsPage extends BasePage{
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='ADD']")
    WebElement btnAdd;

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContacts;

//    @FindBy(xpath = "//a[text()='ABOUT']")
//    WebElement btnAbout;
//
//    @FindBy(xpath = "//a[text()='HOME']")
//    WebElement btnHome;

    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;

    @FindBy(xpath = "//h1[text() =  ' No Contacts here!']")
    WebElement messageContacts;

//    public void clickBtnAdd(){
//        btnAdd.click();
//    }
//
//    public void clickBtnContacts(){
//        btnContacts.click();
//    }

    @FindBy(className = "contact-item_card__2SOIM")
    List<WebElement> listContacts;

//    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
//    WebElement lastContact;

    public boolean isContactPresent(Contact contact){
        for (WebElement element: listContacts){
            if (element.getText().contains(contact.getName()) &&
            element.getText().contains(contact.getPhone())){
                System.out.println(element.getText());
                return true;
            }
        }
        return false;
    }

//    public void clickLastcontact(){
//        lastContact.click();
//    }

    public int getCountOfContacts(){
        return listContacts.size();
    }

    public boolean isBtnAddDisplayed(){
        return isElementDisplayed(btnAdd);
    }

    public boolean isBtnContactsDisplayed(){
        return isElementDisplayed(btnContacts);
    }

    public boolean isTextInBtnSignOutPresent(String text){
        return isTextInElementPresent(btnSignOut,text);
    }

    public boolean isTextInBtnAddPresent(String text){
        return isTextInElementPresent(btnAdd, text);
    }

    public boolean isTextContactMessagePresent(String text){
        return isTextInElementPresent(messageContacts, text);
    }
}
