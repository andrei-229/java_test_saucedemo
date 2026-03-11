package ui.tests;

import org.junit.jupiter.api.*;
import ui.base.UiTestBase;
import ui.pages.CartPage;
import ui.pages.InventoryPage;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTests extends UiTestBase {
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    void setUpCart() {
        login();
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    @Order(1)
    void testAddAndRemoveItem() throws Exception {
        inventoryPage.add_to_cart_n(0);
        inventoryPage.click_cart();

        assertEquals(1, cartPage.getItemCount());
        cartPage.removeItem(0);
        assertEquals(0, cartPage.getItemCount());
    }

    @Test
    @Order(2)
    void testCartBadgeUpdates() throws Exception {
        inventoryPage.addItemsToCart(0, 1);
        assertEquals(2, inventoryPage.getCartBadgeCount());

        inventoryPage.click_cart();
        cartPage.removeItem(0);
        cartPage.clickContinueShopping();

        assertEquals(1, inventoryPage.getCartBadgeCount());
    }
}