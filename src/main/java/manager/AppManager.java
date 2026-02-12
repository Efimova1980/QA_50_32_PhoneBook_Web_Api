package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class AppManager {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setup(){ //инициализация
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // (@BeforeMethod) setup --> (@Test) testName --> (@AfterMethod) tearDown

    @AfterMethod(enabled = true)
    public void tearDown(){ //очистка
        if (driver != null)
            driver.quit();
    }
}
