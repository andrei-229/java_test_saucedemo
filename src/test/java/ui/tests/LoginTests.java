package ui.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.UiTestBase;
import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
public class LoginTests extends UiTestBase {

    @Test
    void testValidLogin() {
        loginPage.loginAs(USERNAME, PASSWORD);
        assertEquals("Swag Labs", driver.getTitle());
    }

    @Test
    void testInvalidLogin() {
        loginPage.loginAs("wrong_user", "wrong_pass");
        String error = loginPage.getErrorMessage();
        assertTrue(error.contains("Username and password do not match"));
    }
}