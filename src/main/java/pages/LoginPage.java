package pages;

import dto.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@name='login']")
    WebElement btnLogin;

    @FindBy(xpath = "//button[@name='registration']")
    WebElement btnRegistration;

    public void typeLoginRegistrationForm(String email, String password){
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
    }

    public void typeLoginRegistrationFormWithUser(User user){
        inputEmail.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnLogin(){
        btnLogin.click();
    }

    public void clickBtnRegistration(){
        btnRegistration.click();
    }
}
