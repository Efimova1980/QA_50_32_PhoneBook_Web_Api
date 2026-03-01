package ui_tests;

import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonHeader;
import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

public class EditContactTests extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;

    @BeforeMethod
    public void login(){
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLogin();
        contactsPage = new ContactsPage(getDriver());
    }

    @Test
    public void EditFirstContactPositiveTest(){
        Contact contact = positiveContact();
        contactsPage.editFirstContact(contact);
        contactsPage.pause(3);
        Assert.assertTrue(contactsPage.isContactPresent(contact));
    }

    @Test
    public void EditFirstContactPositiveTest_WithCheckFirstContact(){
        Contact contact = positiveContact();
        contactsPage.editFirstContact(contact);
        contactsPage.pause(3);
        Assert.assertEquals(contact, contactsPage.getContactFromContactCard());
    }
}
