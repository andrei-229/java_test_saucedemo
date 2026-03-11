package ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ui.base.UiTestBase;
import ui.pages.CartPage;
import ui.pages.InventoryPage;

@Tag("ui")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTests extends UiTestBase {
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    void setUpCart() {
        login();
        inventoryPage = new InventoryPage(driver);
        
    }

    @Test
    @Order(1)
    void testAddAndRemoveItem() throws Exception {
        inventoryPage.add_to_cart_n(0);
        inventoryPage.click_cart();

        cartPage = new CartPage(driver);
        assertEquals(1, cartPage.getItemCount());
        cartPage.removeItem(0);
        assertEquals(0, cartPage.getItemCount());
    }

    @Test
    @Order(2)
    void testCartBadgeUpdates() throws Exception {
        inventoryPage.addItemsToCart(0, 1);
        inventoryPage.waitForCartBadgeCount(2);
        assertEquals(2, inventoryPage.getCartBadgeCount());

        inventoryPage.click_cart();
        cartPage = new CartPage(driver); 
        cartPage.removeItem(0);
        cartPage.clickContinueShopping();

        assertEquals(1, inventoryPage.getCartBadgeCount());
    }
}