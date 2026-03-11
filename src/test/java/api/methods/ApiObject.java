package api.methods;



import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class ApiObject {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    public ApiObject(String apiKey) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", apiKey)   // ← добавляем заголовок
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }


    public Response getUsersPage(int page) {
        return given()
                .spec(requestSpec)
                .queryParam("page", page)
        .when()
                .get("/users");
    }

    // 2. Получить одного пользователя по ID
    public Response getUserById(int id) {
        return given()
                .spec(requestSpec)
        .when()
                .get("/users/{id}", id);
    }

    // 3. Создать нового пользователя (принимает тело в виде Map)
    public Response createUser(Map<String, Object> userData) {
        return given()
                .spec(requestSpec)
                .body(userData)
        .when()
                .post("/users");
    }

    // 4. Полностью обновить пользователя (PUT)
    public Response updateUser(int id, Map<String, Object> userData) {
        return given()
                .spec(requestSpec)
                .body(userData)
        .when()
                .put("/users/{id}", id);
    }

    // 5. Частично обновить пользователя (PATCH)
    public Response patchUser(int id, Map<String, Object> userData) {
        return given()
                .spec(requestSpec)
                .body(userData)
        .when()
                .patch("/users/{id}", id);
    }

    // 6. Удалить пользователя
    public Response deleteUser(int id) {
        return given()
                .spec(requestSpec)
        .when()
                .delete("/users/{id}", id);
    }

    // 7. Регистрация (POST /register)
    public Response register(Map<String, String> credentials) {
        return given()
                .spec(requestSpec)
                .body(credentials)
        .when()
                .post("/register");
    }

    // 8. Логин (POST /login)
    public Response login(Map<String, String> credentials) {
        return given()
                .spec(requestSpec)
                .body(credentials)
        .when()
                .post("/login");
    }

    // 9. Получить список пользователей с задержкой
    public Response getUsersWithDelay(int delaySeconds) {
        return given()
                .spec(requestSpec)
                .queryParam("delay", delaySeconds)
        .when()
                .get("/users");
    }

    

}
