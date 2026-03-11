package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    WebDriver driver;

    WebElement cart_list;

    public CartPage(WebDriver driver) {
        this.driver = driver;

    }

    public String get_n_item_name(int n) throws Exception {
        cart_list = driver.findElement(By.className("cart_list"));
        List<WebElement> elements = cart_list.findElements(By.className("cart_item"));
        try {
            String element = elements.get(n).findElement(By.className("inventory_item_name")).getText();
            return element;
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }
    }

    public Float get_n_item_price(int n) throws Exception {
        cart_list = driver.findElement(By.className("cart_list"));
        List<WebElement> elements = cart_list.findElements(By.className("cart_item"));
        try {
            String element = elements.get(n).findElement(By.className("inventory_item_price")).getText();
            return Float.parseFloat(element.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }
    }
    public int getItemCount() {
    WebElement cartList = driver.findElement(By.className("cart_list"));
    return cartList.findElements(By.className("cart_item")).size();
    }

    // Удалить товар по индексу (0-based)
    public void removeItem(int index) throws Exception {
        WebElement cartList = driver.findElement(By.className("cart_list"));
        List<WebElement> items = cartList.findElements(By.className("cart_item"));
        if (index >= items.size()) {
            throw new Exception("IndexOutOfBoundsException");
        }
        WebElement removeButton = items.get(index).findElement(By.tagName("button"));
        if (!"Remove".equals(removeButton.getText())) {
            throw new Exception("Button text is not 'Remove'");
        }
        removeButton.click();
    }

    // Перейти к оформлению заказа
    public void clickCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

    // Вернуться к покупкам
    public void clickContinueShopping() {
        driver.findElement(By.id("continue-shopping")).click();
    }

}
