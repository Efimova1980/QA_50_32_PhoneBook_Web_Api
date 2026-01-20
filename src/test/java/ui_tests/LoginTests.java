package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends AppManager {
    @Test
    public void loginPositiveTest(){
        new HomePage(getDriver()).clickBtnlogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm("elton.john@gmail.com", "$John250347$");
        loginPage.clickBtnLogin();

    }

    @Test
    public void loginPositiveTestWithUser(){
        User user = new User("elton.john@gmail.com", "$John250347$");
        new HomePage(getDriver()).clickBtnlogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLogin();

    }

}
