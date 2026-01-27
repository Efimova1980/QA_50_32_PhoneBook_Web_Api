package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.UserFactory;
import java.util.Random;

public class RegistrationTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage(){
        new HomePage(getDriver()).clickBtnlogin();
        loginPage = new LoginPage(getDriver());
    }

    //----------------------------POSITIVE TESTS-------------------------------
    @Test
    public void registrationPositiveTest_Password_8Chars(){
        Random rn = new Random();
        String email = "mail" + rn.nextInt(1000) + "@gmail.com";
        User user = new User(email, "Pass123!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver()).isTextContactMessagePresent("No Contacts here!"));
    }

    @Test
    public void registrationPositiveTest_Password15Chars(){
        Random rn = new Random();
        String email = "mail" + rn.nextInt(1000) + "@gmail.com";
        User user = new User(email, "Pass1234567890!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver()).isTextContactMessagePresent("No Contacts here!"));
    }

    @Test
    public void registrationPositiveTest_WithFaker(){
        User user = UserFactory.positiveUser();
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver()).isTextContactMessagePresent("No Contacts here!"));
    }

    /*
    -------------------------NEGATIVE TESTS - VALID EMAIL NONVALID PASSWORD--------------------
    invalid password:
    (empty field)	-T7
    (blanked string) -T8
    Pass1234	- T9 no special chars
    Pass1234!ш	- T10 - cyrillic letters
    pass1234!	- T11 - no big letters
    PASS1234!	- T12 - no small letters
    Password!	- T13 - no digits
    Pass12!	- T14 - < 8 symbols
    Pass1234!xxxxxxxxxx	-T15 > 15 chars
     */

    @Test
    public void registrationNegativeTest_ValidEmail_EmptyPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_BlankedPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("          ");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_NoSpecCharInPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("Pass1234");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_CyrLetterInPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("Pass1234!ш");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_NoBigLettersInPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("pass1234!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_NoSmallLettersInPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("PASS1234!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_NoDigitsInPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("Password!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_ShortPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("Pass12!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidEmail_LongPassword(){
        User user = UserFactory.positiveUser();
        user.setPassword("Pass1234!xxxxxxxxxxxxxx");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    /*
    -------------------------NEGATIVE TESTS - VALID PASSWORD NONVALID EMAIL--------------------
    invalid email:
    (empty field)	-T1
    (blanked string)	-T2
    mailgmail.com	-T3 no char '@'
    mail@@gmail.com	-T3 more than one char '@'
    @gmail.com	-T4 no chars before '@'
    mail@	-T5  no chars after '@'
    почта@gmail.com	-T6 cyrillic chars in email
     */

    @Test
    public void registrationNegativeTest_ValidPass_EmptyEmail(){
        User user = UserFactory.positiveUser();
        user.setUsername("");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidPass_BlankedEmail(){
        User user = UserFactory.positiveUser();
        user.setUsername("          ");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidPass_NoMonkeyInEmail(){
        Random rn = new Random();
        String email = "mail" + rn.nextInt(1000) + "gmail.com";

        User user = UserFactory.positiveUser();
        user.setUsername(email);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidPass_TwoMonkeysInEmail(){
        Random rn = new Random();
        String email = "mail" + rn.nextInt(1000) + "@@gmail.com";

        User user = UserFactory.positiveUser();
        user.setUsername(email);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidPass_NoCharsBeforeMonkeyInEmail(){
        Random rn = new Random();
        String email = "@" + rn.nextInt(1000) + "gmail.com";

        User user = UserFactory.positiveUser();
        user.setUsername(email);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidPass_NoCharsAfterMonkeyInEmail(){
        Random rn = new Random();
        String email = "mail" + rn.nextInt(1000) + "@";

        User user = UserFactory.positiveUser();
        user.setUsername(email);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTest_ValidPass_CyrCharsInEmail(){
        Random rn = new Random();
        String email = "почта" + rn.nextInt(1000) + "@gmail.com";

        User user = UserFactory.positiveUser();
        user.setUsername(email);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    //-------------------------NEGATIVE TESTS - ALREADY REGISTERED USER
    @Test
    public void registrationNegativeTest_UserAlreadyExists(){
        User user = new User("elton.john@gmail.com", "$John250347$");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistration();
        Assert.assertEquals(loginPage.closeAlertReturnText(),"User already exist");
    }


}
