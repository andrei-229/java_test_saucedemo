package ui.tests;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import io.github.bonigarcia.wdm.WebDriverManager;
import ui.pages.CartPage;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;

import org.openqa.selenium.firefox.FirefoxDriver;

import utils.WebDriverFactory;

@Execution(ExecutionMode.CONCURRENT)
public class FirstTest {

    // private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private String USERNAME = "standard_user";
    private String PASSWORD = "secret_sauce";

    @BeforeEach
    void setUp() {
        driver = WebDriverFactory.createDriver(); // создадим утилиту позже
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        loginPage.open();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testValidLogin() {
        loginPage.loginAs(USERNAME, PASSWORD);
        Assertions.assertEquals("Swag Labs", inventoryPage.getPageTitle());
    }

    @Test
    void testInvalidLogin() {
        loginPage.loginAs("wrong_user", "wrong_pass");
        String error = loginPage.getErrorMessage();
        Assertions.assertTrue(error.contains("Username and password do not match"));
    }

    @Test
    void testSortName() throws Exception {
        loginPage.loginAs(USERNAME, PASSWORD);

        List<String> tmp = inventoryPage.get_str_list();
        System.out.println(tmp);

        inventoryPage.change_sort(1);

        List<String> tmp2 = inventoryPage.get_str_list();
        System.out.println(tmp2);

        Assertions.assertNotEquals(tmp, tmp2);

        tmp.sort(null);
        List<String> tmp3 = tmp.reversed();

        Assertions.assertEquals(tmp2, tmp3);

    }

    @Test
    void testSortPrice() throws Exception {
        loginPage.loginAs(USERNAME, PASSWORD);

        inventoryPage.change_sort(2);

        List<Float> tmp = inventoryPage.get_float_list();
        List<Float> copy = new ArrayList<>(tmp);
        copy.sort(null);

        Assertions.assertEquals(tmp, copy);
        System.out.println(tmp);

        inventoryPage.change_sort(3);

        List<Float> tmp2 = inventoryPage.get_float_list();

        Assertions.assertEquals(tmp2, copy.reversed());
    }

    @Test
    void testAddCart() throws Exception {
        loginPage.loginAs(USERNAME, PASSWORD);
        String item_name = inventoryPage.get_n_element_name(0);
        inventoryPage.add_to_cart_n(0);
        inventoryPage.click_cart();

        String item_copy = cartPage.get_n_item_name(0);
        Assertions.assertEquals(item_name, item_copy);
    }
}