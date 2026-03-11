package ui.tests;

import org.junit.jupiter.api.*;
import ui.base.UiTestBase;
import ui.pages.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckoutTests extends UiTestBase {
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    void setUpCheckout() {
        login();
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    @Order(1)
    void testFullCheckout() throws Exception {
        inventoryPage.addItemsToCart(0, 1);
        inventoryPage.click_cart();

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