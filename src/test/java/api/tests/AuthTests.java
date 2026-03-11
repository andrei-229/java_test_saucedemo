package api.tests;

import api.base.ApiTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.hamcrest.Matchers.*;

@Tag("api")
public class AuthTests extends ApiTestBase {

    @Test
    void testRegisterSuccessful() {
        Map<String, String> creds = Map.of(
                "email", "eve.holt@reqres.in",
                "password", "pistol"
        );
        Response response = api.register(creds);
        response.then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void testRegisterUnsuccessful() {
        Map<String, String> creds = Map.of("email", "eve.holt@reqres.in");
        Response response = api.register(creds);
        response.then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    void testLoginSuccessful() {
        Map<String, String> creds = Map.of(
                "email", "eve.holt@reqres.in",
                "password", "cityslicka"
        );
        Response response = api.login(creds);
        response.then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void testLoginUnsuccessful() {
        Map<String, String> creds = Map.of("email", "peter@klaven");
        Response response = api.login(creds);
        response.then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}