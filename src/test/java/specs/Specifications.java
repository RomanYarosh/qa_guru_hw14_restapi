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

    private static final String API_KEY = "reqres_a37e4386c6fa47fbb39791e6a1d6f4bc";

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .addFilter(new AllureRestAssured())
            .addHeader("x-api-key", API_KEY)
            .setContentType(ContentType.JSON)
            .log(URI)
            .log(BODY)
            .log(HEADERS)
            .build();

    public static ResponseSpecification responseSpec(int status) {
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
