import io.qameta.allure.*;
import models.UserRequest;
import models.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.Specifications;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresExtendedTests {

    @Test
    @DisplayName("Получение списка пользователей")
    void getListUsersTest() {
        step("Выполняем GET запрос для получения списка пользователей", () -> {
            given()
                    .spec(Specifications.requestSpec)
                    .queryParam("page", "2")
            .when()
                    .get("/users")
            .then()
                    .spec(Specifications.responseSpec(200))
                    .body("page", is(2));
        });
    }

    @Test
    @DisplayName("Создание нового пользователя")
    void createUserTest() {
        UserRequest user = UserRequest.builder()
                .name("ivan")
                .job("qa")
                .build();

        UserResponse response = step("Создание пользователя ivan/qa", () ->
                given()
                        .spec(Specifications.requestSpec)
                        .body(user)
                .when()
                        .post("/users")
                .then()
                        .spec(Specifications.responseSpec(201))
                        .extract()
                        .as(UserResponse.class)
        );

        step("Сверка имени в ответе", () -> {
            assertEquals(user.getName(), response.getName());
        });
    }

    @Test
    @DisplayName("Получение данных одного пользователя")
    void getSingleUserTest() {
        step("Запрос данных пользователя с ID 2", () -> {
            given()
                    .spec(Specifications.requestSpec)
            .when()
                    .get("/users/2")
            .then()
                    .spec(Specifications.responseSpec(200))
                    .body("data.id", is(2));
        });
    }

    @Test
    @DisplayName("Полное обновление пользователя (PUT)")
    void updateUserTest() {
        UserRequest user = UserRequest.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        UserResponse response = step("Обновление пользователя ID 2", () ->
                given()
                        .spec(Specifications.requestSpec)
                        .body(user)
                .when()
                        .put("/users/2")
                .then()
                        .spec(Specifications.responseSpec(200))
                        .extract()
                        .as(UserResponse.class)
        );

        step("Сверка обновленной профессии", () -> {
            assertEquals("zion resident", response.getJob());
        });
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        step("Удаление пользователя с ID 2", () -> {
            given()
                    .spec(Specifications.requestSpec)
            .when()
                    .delete("/users/2")
            .then()
                    .spec(Specifications.responseSpec(204));
        });
    }
}
