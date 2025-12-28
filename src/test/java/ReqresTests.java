import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTests extends BaseTest {

    @Test
    @DisplayName("Проверка списка пользователей")
    public void getListUsersTest() {
        given()
                .log().uri()
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .log().status()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Проверка одного пользователя")
    public void getSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"));
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUserTest() {
        String body = "{\"name\": \"ivan\", \"job\": \"qa\"}";

        given()
                .log().uri()
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().status()
                .statusCode(201)
                .body("name", equalTo("ivan"))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Обновление данных (PUT)")
    public void updateUserTest() {
        String body = "{\"name\": \"ivan\", \"job\": \"senior qa\"}";

        given()
                .log().uri()
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .statusCode(200)
                .body("job", equalTo("senior qa"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {
        given()
                .log().uri() // Логируем только URI запроса
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }
}
