package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private By items = By.className("inventory_item");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получить название товара №{n}")
    public String get_n_element_name(int n) throws Exception {
        List<WebElement> elements = driver.findElements(items);
        try {
            return elements.get(n).findElement(By.className("inventory_item_name")).getText();
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }
    }

    @Step("Получить цену товара №{n}")
    public Float get_n_element_price(int n) throws Exception {
        List<WebElement> elements = driver.findElements(items);
        try {
            String priceText = elements.get(n).findElement(By.className("inventory_item_price")).getText();
            return Float.parseFloat(priceText.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }
    }

    @Step("Изменить сортировку на индекс: {idx}")
    public void change_sort(int idx) {
        WebElement selectElement = driver.findElement(By.className("product_sort_container"));
        Select select = new Select(selectElement);
        select.selectByIndex(idx);
    }

    @Step("Добавить товар №{n} в корзину")
    public void add_to_cart_n(int n) throws Exception {
        WebElement button = driver.findElements(items).get(n).findElement(By.tagName("button"));
        if (!button.getText().equals("Add to cart")) {
            throw new Exception("NotFoundButton");
        }
        button.click();
    }

    @Step("Перейти в корзину")
    public void click_cart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @Step("Получить значение счётчика корзины")
    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(By.className("shopping_cart_badge"));
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }
}