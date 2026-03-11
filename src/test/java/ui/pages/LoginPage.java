package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу логина")
    public void open() {
        driver.get("https://www.saucedemo.com/");
    }

    @Step("Ввести имя пользователя: {username}")
    public void enterUsername(String username) {
        driver.findElement(usernameInput).sendKeys(username);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку логина")
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    @Step("Выполнить вход с логином: {username}")
    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Получить сообщение об ошибке")
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}