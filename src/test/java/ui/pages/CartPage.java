package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получить название товара №{n} в корзине")
    public String get_n_item_name(int n) throws Exception {
        WebElement cartList = driver.findElement(By.className("cart_list"));
        List<WebElement> elements = cartList.findElements(By.className("cart_item"));
        try {
            return elements.get(n).findElement(By.className("inventory_item_name")).getText();
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }
    }

    @Step("Получить цену товара №{n} в корзине")
    public Float get_n_item_price(int n) throws Exception {
        WebElement cartList = driver.findElement(By.className("cart_list"));
        List<WebElement> elements = cartList.findElements(By.className("cart_item"));
        try {
            String priceText = elements.get(n).findElement(By.className("inventory_item_price")).getText();
            return Float.parseFloat(priceText.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }
    }

    @Step("Получить количество товаров в корзине")
    public int getItemCount() {
        WebElement cartList = driver.findElement(By.className("cart_list"));
        return cartList.findElements(By.className("cart_item")).size();
    }

    @Step("Удалить товар №{index} из корзины")
    public void removeItem(int index) throws Exception {
        WebElement cartList = driver.findElement(By.className("cart_list"));
        List<WebElement> items = cartList.findElements(By.className("cart_item"));
        if (index >= items.size()) {
            throw new Exception("IndexOutOfBoundsException");
        }
        WebElement removeButton = items.get(index).findElement(By.tagName("button"));
        removeButton.click();
    }

    @Step("Нажать кнопку Checkout")
    public void clickCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

    @Step("Вернуться к покупкам")
    public void clickContinueShopping() {
        driver.findElement(By.id("continue-shopping")).click();
    }
}