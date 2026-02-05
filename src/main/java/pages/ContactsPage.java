package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

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
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;
    @FindBy(xpath = "//h1[text() =  ' No Contacts here!']")
    WebElement messageContacts;
    @FindBy(className = "contact-item_card__2SOIM")
    List<WebElement> listContacts;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
    WebElement lastContact;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div")
    WebElement divListContacts;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactCard;

    public void clickLastContact(){
        lastContact.click();
    }

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

    public boolean isContactPresentInTheContactCard(Contact contact){

            if (contactCard.getText().contains(contact.getName()) &&
                    contactCard.getText().contains(contact.getLastName()) &&
                    contactCard.getText().contains(contact.getEmail()) &&
                    contactCard.getText().contains(contact.getPhone()) &&
                    contactCard.getText().contains(contact.getAddress())){
                System.out.println(contactCard.getText());
                return true;
            }
        return false;
    }

    public void scrollToLastContact(){
        Actions actions = new Actions(driver);
        actions.scrollToElement(lastContact).perform();
    }

    public void scrollToLastContactWithOrigin(){
        Actions actions = new Actions(driver);
        int deltaY = divListContacts.getSize().getHeight();
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin
                .fromElement(divListContacts);
        actions.scrollFromOrigin(scrollOrigin, 0, deltaY).perform();
    }

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
