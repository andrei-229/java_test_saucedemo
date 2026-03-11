package ui.tests;

import org.junit.jupiter.api.*;
import ui.base.UiTestBase;
import ui.pages.InventoryPage;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryTests extends UiTestBase {
    private InventoryPage inventoryPage;

    @BeforeEach
    void setUpInventory() {
        login(); // из базового класса
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    @Order(1)
    void testSortNameDescending() throws Exception {
        List<String> original = inventoryPage.get_str_list();
        inventoryPage.change_sort(1); // Z to A
        List<String> sorted = inventoryPage.get_str_list();
        assertNotEquals(original, sorted);
        original.sort(null);
        assertEquals(sorted, original.reversed());
    }

    @Test
    @Order(2)
    void testSortPriceLowToHigh() {
        inventoryPage.change_sort(2); // Price low to high
        List<Float> prices = inventoryPage.get_float_list();
        List<Float> sorted = new ArrayList<>(prices);
        sorted.sort(null);
        assertEquals(prices, sorted);
    }

    @Test
    @Order(3)
    void testAddToCartFromInventory() throws Exception {
        String itemName = inventoryPage.get_n_element_name(0);
        inventoryPage.add_to_cart_n(0);
        assertEquals(1, inventoryPage.getCartBadgeCount());
        inventoryPage.click_cart();
        // проверка в корзине будет в CartTests
    }
}