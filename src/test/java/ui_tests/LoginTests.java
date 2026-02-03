package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.RetryAnalyser;
import utils.UserFactory;

public class LoginTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage(){
        new HomePage(getDriver()).clickBtnlogin();
        loginPage = new LoginPage(getDriver());
    }

    //-----------------------POSITIVE TESTS ----------------------------------
    @Test(retryAnalyzer = RetryAnalyser.class)
    public void loginPositiveTest(){
        loginPage.typeLoginRegistrationForm("elton.john@gmail.com", "$John250347$");
        loginPage.clickBtnLogin();
        //Assert.assertTrue(new ContactsPage(getDriver()).isBtnAddDisplayed());
        //Assert.assertTrue(new ContactsPage(getDriver()).isTextInBtnAddPresent("ADD"));
        Assert.assertTrue(new ContactsPage(getDriver()).isTextInBtnSignOutPresent("Sign Out"));
    }

    @Test
    public void loginPositiveTestWithUser(){
        User user = new User("elton.john@gmail.com", "$John250347$");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertTrue(new ContactsPage(getDriver()).isBtnContactsDisplayed());
    }

    //-----------------------NEGATIVE TESTS ----------------------------------
    @Test
    public void loginNegativeTest_WrongEmail(){
        User user = new User("elton.johngmail.com", "$John250347$");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

    @Test
    public void loginNegativeTest_WrongPassword(){
        User user = new User("elton.john@gmail.com", "$John250347$   ");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

    @Test
    public void loginNegativeTest_SpacesBeforeAndAfterMail(){
        //spaces around email must be ignored
        User user = new User("  elton.john@gmail.com  ", "$John250347$");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertTrue(new ContactsPage(getDriver()).isBtnContactsDisplayed());
    }

    @Test
    public void loginNegativeTest_EmptyFields(){
        User user = new User("", "");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

    @Test
    public void loginNegativeTest_EmptyEmail(){
        User user = new User("", "$John250347$");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

    @Test
    public void loginNegativeTest_EmptyPassword(){
        User user = new User("elton.john@gmail.com", "");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

    //-----------------------NEGATIVE TEST - USER WITH VALID DATA WAS NOT REGISTERED ----------------------------------
    @Test
    public void loginNegativeTest_UserIsNotRegistered(){
        User user = UserFactory.positiveUser();
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlertReturnText(), "Wrong email or password");
    }

}
