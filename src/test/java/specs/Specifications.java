package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.*;

public class Specifications {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .addFilter(new AllureRestAssured())
            .addHeader("x-api-key", "reqres_a37e4386c6fa47fbb39791e6a1d6f4bc")
            .setContentType(ContentType.JSON)
            .log(URI)
            .log(METHOD) // Полезно видеть метод в логах
            .log(BODY)
            .build();

    public static ResponseSpecification responseSpec(int status) {
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
