package ui_tests;

import manager.AppManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import java.time.Duration;

import static pages.BasePage.clickButtonHeader;
import static utils.PropertiesReader.getProperty;

public class DeleteContactTest extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    int countOfContacts;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLogin();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
    }

    @Test
    public void deleteFirstContactPositiveTest() {
        if (countOfContacts == 0) return;
        contactsPage.deleteFistContact();
        contactsPage.pause(3);
        Assert.assertEquals(contactsPage.getCountOfContacts(), countOfContacts-1);
    }
}
