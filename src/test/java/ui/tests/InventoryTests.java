package ui.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import ui.base.UiTestBase;
import ui.pages.InventoryPage;

@Tag("ui")
@DisplayName("Тесты сортировки на странице товаров")
public class InventoryTests extends UiTestBase {
    private InventoryPage inventoryPage;

    @BeforeEach
    void setUpInventory() {
        login();
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    @DisplayName("Сортировка по имени (Z -> A)")
    void testSortNameDescending() throws Exception {
        List<String> original = inventoryPage.get_str_list();
        inventoryPage.change_sort(1); // Z to A
        List<String> sorted = inventoryPage.get_str_list();
        assertNotEquals(original, sorted);
        
        // Сортируем оригинальный список по убыванию вручную
        List<String> expected = new ArrayList<>(original);
        Collections.sort(expected);
        Collections.reverse(expected);
        
        assertEquals(expected, sorted);
    }

    @Test
    @DisplayName("Сортировка по цене (low to high)")
    void testSortPriceLowToHigh() {
        inventoryPage.change_sort(2); // Price low to high
        List<Float> prices = inventoryPage.get_float_list();
        List<Float> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        assertEquals(prices, sorted);
    }

    @Test
    @DisplayName("Сортировка по цене (high to low)")
    void testSortPriceHighToLow() {
        inventoryPage.change_sort(3); // Price high to low
        List<Float> prices = inventoryPage.get_float_list();
        List<Float> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Collections.reverse(sorted);
        assertEquals(prices, sorted);
    }
}