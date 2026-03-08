package ui_tests;

import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonHeader;
import static utils.PropertiesReader.getProperty;

public class DeleteContactTest extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    int countOfContacts;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLogin();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
    }

    @Test(groups = {"smoke", "contact"})
    public void deleteFirstContactPositiveTest() {
        contactsPage.deleteFistContact();
//        contactsPage.pause(3); //если в методе нет WebDriverWait
        Assert.assertEquals(contactsPage.getCountOfContacts(), countOfContacts-1);
    }

    @Test
    public void deleteFirstContactPositiveTest_WithCheckFirstContact() {
        Contact contactDeleted = contactsPage.deleteFistContact_WithCheckFirstContact();
        //contactsPage.pause(3);  //если в методе нет WebDriverWait
        Assert.assertFalse(contactsPage.isContactPresent(contactDeleted));
    }

}
