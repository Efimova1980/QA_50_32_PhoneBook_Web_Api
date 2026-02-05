package ui_tests;

import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import static utils.ContactFactory.*;
import utils.HeaderMenuItem;
import static pages.BasePage.*;
import static utils.PropertiesReader.getProperty;


public class AddNewContactTests extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    int countOfContacts;

    @BeforeMethod
    public void login(){
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLogin();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
        addPage = clickButtonHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest(){
        addPage.typeContactForm(positiveContact());
        addPage.clickBtnSave();
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContactsAfterAdd, countOfContacts + 1);
    }

    @Test
    public void addNewContactPositiveTest_ClickLastContact(){
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        addPage.clickBtnSave();
        Assert.assertTrue(contactsPage.isContactPresent(contact));
    }

    @Test
    public void addNewContactPositiveTest_ScrollToLastcontact(){
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        addPage.clickBtnSave();
        contactsPage.scrollToLastContactWithOrigin();
        //contactsPage.scrollToLastContact();
        contactsPage.clickLastContact();
        Assert.assertTrue(contactsPage.isContactPresentInTheContactCard(contact));
    }
}
