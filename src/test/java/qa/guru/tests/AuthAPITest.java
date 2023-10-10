package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.AuthBodyModel;
import qa.guru.models.AuthResponseModel;
import qa.guru.models.AuthErrorModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static qa.guru.spec.AuthSpec.*;

public class AuthAPITest {

    @Test
    @DisplayName("Успешная авторизация")
    void successfulAuthTest() {

        AuthBodyModel authData = new AuthBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        AuthResponseModel response = step("Login request",()->
             given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post("/login")
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(AuthResponseModel.class));
    step("Verify response", ()->
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));


    }

    @Test
    @DisplayName("Пороверка ошибки авторизации, при отсутствии пароля")
    void unsuccessfulAuthTest() {
        AuthBodyModel authData = new AuthBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("");

        AuthErrorModel response = step("Make missing password request",()->
        given(loginRequestSpec)
                .body(authData)
                .when()
                .post("/login")
                .then()
                .spec(missingPasswordResponseSpec)
                .extract().as(AuthErrorModel.class));

        step("Verify response", ()->
                assertEquals("Missing password", response.getError()));
    }


    @Test
    @DisplayName("Successful user deletion")
    void successfulUserDeleteTest() {
        String deletedUser = "{\n" +
                "    \"name\": \"Ezio Auditore\",\n" +
                "    \"job\": \"Assassin\"\n" +
                "}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(deletedUser)
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

    }

    @Test
    @DisplayName("Single resource")
    void singleResourceTest() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.name", is("fuchsia rose"))
                .body("data.year", is(2001));
    }

    @Test
    @DisplayName("Can't find single resource")
    void singleResourceNotFoundTest() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/unknown/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);

    }

    @Test
    @DisplayName("User Update")
    void userUpdateTest() {
        String createdUser = "{\n" +
                "    \"name\": \"Ezio Auditore\",\n" +
                "    \"job\": \"Assassin\"\n" +
                "}";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(createdUser)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Ezio Auditore"))
                .body("job", is("Assassin"))
                .body("updatedAt", notNullValue());
    }

}
