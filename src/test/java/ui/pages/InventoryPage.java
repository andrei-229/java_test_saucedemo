package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {

    private WebDriver driver;

    private By items = By.className("inventory_item");

    private Select select;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String get_n_element_name(int n) throws Exception {
        List<WebElement> elements = driver.findElements(items);
        try {
            return elements.get(n).findElement(By.className("inventory_item_name")).getText();
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }

    }

    public Float get_n_element_price(int n) throws Exception {
        List<WebElement> elements = driver.findElements(items);
        try {
            String priceText = elements.get(n).findElement(By.className("inventory_item_price")).getText();
            return Float.parseFloat(priceText.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            throw new Exception("IndexOutOfBoundsException");
        }

    }

    public List<Float> get_float_list() {
        List<WebElement> elements = driver.findElements(items);
        List<Float> ans = new ArrayList<>();
        for (WebElement element : elements) {
            String priceText = element.findElement(By.className("inventory_item_price")).getText();

            Float price = Float.parseFloat(priceText.replaceAll("[^0-9.]", ""));

            ans.add(price);
        }
        return ans;
    }

    public List<String> get_str_list() {
        List<WebElement> elements = driver.findElements(items);
        List<String> ans = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.findElement(By.className("inventory_item_name")).getText();

            ans.add(text);
        }
        return ans;
    }

    public void change_sort(int idx) {
        WebElement selecElement = driver.findElement(By.className("product_sort_container"));
        select = new Select(selecElement);
        select.selectByIndex(idx);

    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void add_to_cart_n(int n) throws Exception{
        WebElement button = driver.findElements(items).get(n).findElement(By.tagName("button"));
        if (!button.getText().equals("Add to cart")){
            throw new Exception("NotFoundButton");
        }
        button.click();
        
    }

    public void remove_to_cart_n(int n) throws Exception{
        WebElement button = driver.findElements(items).get(n).findElement(By.tagName("button"));
        if (!button.getText().equals("Remove")){
            throw new Exception("NotFoundButton");
        }
        button.click();
        
    }

    public void click_cart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    public String get_name_item(WebElement item) {
        return item.findElement(By.className("inventory_item_name")).getText();
    }

    public int getCartBadgeCount() {
    List<WebElement> badges = driver.findElements(By.className("shopping_cart_badge"));
    return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    // Добавить несколько товаров по индексам
    public void addItemsToCart(int... indices) throws Exception {
        for (int index : indices) {
            add_to_cart_n(index);
        }
    }
}