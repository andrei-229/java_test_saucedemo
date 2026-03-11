package ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ui.base.UiTestBase;
import ui.pages.CartPage;
import ui.pages.CheckoutCompletePage;
import ui.pages.CheckoutStepOnePage;
import ui.pages.CheckoutStepTwoPage;
import ui.pages.InventoryPage;

@Tag("ui")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckoutTests extends UiTestBase {
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    void setUpCheckout() {
        login();
        inventoryPage = new InventoryPage(driver);
        
    }

    @Test
    @Order(1)
    void testFullCheckout() throws Exception {
        inventoryPage.addItemsToCart(0, 1);
        inventoryPage.waitForCartBadgeCount(2); 
        inventoryPage.click_cart();
        
        cartPage = new CartPage(driver);
        float price1 = cartPage.get_n_item_price(0);
        float price2 = cartPage.get_n_item_price(1);

        cartPage.clickCheckout();

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(driver);
        stepOne.fillCheckoutInfo("John", "Doe", "12345");
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        assertEquals(2, stepTwo.getItemCount());
        assertEquals(price1 + price2, stepTwo.getSubtotal(), 0.001);
        stepTwo.clickFinish();

        CheckoutCompletePage complete = new CheckoutCompletePage(driver);
        assertTrue(complete.getCompleteHeaderText().contains("Thank you for your order"));
    }
}