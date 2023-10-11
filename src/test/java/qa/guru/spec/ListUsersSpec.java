package qa.guru.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static qa.guru.helpers.CustomAllureListener.withCustomTemplates;

public class ListUsersSpec {

    public static RequestSpecification listUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification listUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();
}
