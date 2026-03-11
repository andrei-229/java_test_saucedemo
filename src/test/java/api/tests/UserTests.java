package api.tests;

import api.base.ApiTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.hamcrest.Matchers.*;

@Tag("api")
public class UserTests extends ApiTestBase {

    @Test
    void testGetUsersPage2() {
        Response response = api.getUsersPage(2);
        response.then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()))
                .body("data[0].id", notNullValue())
                .body("data[0].email", containsString("@"));
    }

    @Test
    void testGetUserById() {
        Response response = api.getUserById(2);
        response.then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    void testGetUserNotFound() {
        Response response = api.getUserById(23);
        response.then().statusCode(404);
    }

    @Test
    void testCreateUser() {
        Map<String, Object> newUser = Map.of(
                "name", "John Doe",
                "job", "Tester"
        );
        Response response = api.createUser(newUser);
        response.then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("job", equalTo("Tester"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    void testUpdateUser() {
        Map<String, Object> updateData = Map.of(
                "name", "John Smith",
                "job", "Senior Tester"
        );
        Response response = api.updateUser(2, updateData);
        response.then()
                .statusCode(200)
                .body("name", equalTo("John Smith"))
                .body("job", equalTo("Senior Tester"))
                .body("updatedAt", notNullValue());
    }

    @Test
    void testPatchUser() {
        Map<String, Object> patchData = Map.of("job", "Lead Tester");
        Response response = api.patchUser(2, patchData);
        response.then()
                .statusCode(200)
                .body("job", equalTo("Lead Tester"))
                .body("updatedAt", notNullValue());
    }

    @Test
    void testDeleteUser() {
        Response response = api.deleteUser(2);
        response.then().statusCode(204);
    }
}