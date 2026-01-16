package ui_tests;

import manager.AppManager;
import org.testng.annotations.Test;
import pages.HomePage;

import java.util.HashSet;

public class LoginTests extends AppManager {
    @Test
    public void loginPositiveTest(){
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnlogin();
    }
}
