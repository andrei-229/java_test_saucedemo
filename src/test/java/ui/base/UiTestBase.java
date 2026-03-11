package ui.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import ui.pages.LoginPage;
import utils.WebDriverFactory;

// @ExtendWith(AllureScreenshotExtension.class)
// @Execution(ExecutionMode.CONCURRENT)
public class UiTestBase {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected final String USERNAME = "standard_user";
    protected final String PASSWORD = "secret_sauce";

    @BeforeEach
    public void setUp() {
        // Получаем браузер из системного свойства, по умолчанию firefox
        String browser = System.getProperty("browser", "firefox");
        driver = WebDriverFactory.createDriver(browser);
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login() {
        loginPage.loginAs(USERNAME, PASSWORD);
    }
}