package pages;

import dto.Contact;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/button[2]")
    WebElement btnRemove;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/button[1]")
    WebElement btnEdit;

    //--------------------fields from edit card--------------------------------
    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement editName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement editLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement editPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement editEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement editAddress;
    @FindBy(xpath = "//input[@placeholder='desc']")
    WebElement editDesc;
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/button")
    WebElement btnSave;

    public void deleteFistContact(){
        int beforeCount = listContacts.size();
        listContacts.get(0).click();
        btnRemove.click();
        //ждем, пока количество элементов в списке контактов не станет меньше, чем было
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeLessThan(By.className("contact-item_card__2SOIM"), beforeCount));
    }

    public Contact getContactFromContactCard(){
        Contact contact = new Contact();
        String[] dataFistContact = contactCard.getText().split("\n"); //[5]
        contact.setName(dataFistContact[0].split(" ")[0]);
        contact.setLastName(dataFistContact[0].split(" ")[1]);
        contact.setPhone(dataFistContact[1]);
        contact.setEmail(dataFistContact[2]);
        contact.setAddress(dataFistContact[3]);
        contact.setDescription(dataFistContact[4]);
        return contact;
    }

    public Contact deleteFistContact_WithCheckFirstContact() {
        int beforeCount = listContacts.size();
        listContacts.get(0).click();
        Contact contactForDelete = getContactFromContactCard();
        btnRemove.click();
        //ждем, пока количество элементов в списке контактов не станет меньше, чем было
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeLessThan(By.className("contact-item_card__2SOIM"), beforeCount));
        return contactForDelete;
    }

    public void clickLastContact(){
        lastContact.click();
    }

    public boolean isContactPresent(Contact contact){
        for (WebElement element: listContacts){
            if (element.getText().contains(contact.getName()) &&
                element.getText().contains(contact.getPhone())){
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

    public void editFirstContact(Contact contact) {
        listContacts.get(0).click();
        btnEdit.click();

        editName.clear();
        editName.sendKeys(contact.getName());
        editLastName.clear();
        editLastName.sendKeys(contact.getLastName());
        editPhone.clear();
        editPhone.sendKeys(contact.getPhone());
        editEmail.clear();
        editEmail.sendKeys(contact.getEmail());
        editAddress.clear();
        editAddress.sendKeys(contact.getAddress());
        //bug in the field description during editing
        //editDesc.clear();
        //editDesc.sendKeys(contact.getDescription());
        btnSave.click();
    }
}
