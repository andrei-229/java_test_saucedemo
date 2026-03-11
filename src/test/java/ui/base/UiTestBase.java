package ui.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ui.pages.LoginPage;
import utils.WebDriverFactory;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class UiTestBase {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected final String USERNAME = "standard_user";
    protected final String PASSWORD = "secret_sauce";

    @BeforeEach
    public void setUp() {
        driver = WebDriverFactory.createDriver();
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