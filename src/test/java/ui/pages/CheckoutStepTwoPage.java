package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage {
    private WebDriver driver;

    private By cartItems = By.className("cart_item");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By subtotalLabel = By.className("summary_subtotal_label");

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    // Получить сумму товаров до налога (Item total)
    public float getSubtotal() {
        String subtotalText = driver.findElement(subtotalLabel).getText();
        // Формат: "Item total: $29.99"
        String priceStr = subtotalText.replaceAll("[^0-9.]", "");
        return Float.parseFloat(priceStr);
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }
}