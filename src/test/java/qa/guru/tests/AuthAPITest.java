package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.AuthBodyModel;
import qa.guru.models.AuthResponseModel;
import qa.guru.models.AuthErrorModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static qa.guru.spec.AuthSpec.*;

public class AuthAPITest {

    @Test
    @DisplayName("Successful authorization")
    void successfulAuthTest() {

        AuthBodyModel authData = new AuthBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        AuthResponseModel response = step("Login request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(AuthResponseModel.class));
        step("Verify response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));


    }

    @Test
    @DisplayName("Checking for authorization errors if there is no password")
    void unsuccessfulAuthTest() {
        AuthBodyModel authData = new AuthBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("");

        AuthErrorModel response = step("Make missing password request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(missingPasswordResponseSpec)
                        .extract().as(AuthErrorModel.class));

        step("Verify response", () ->
                assertEquals("Missing password", response.getError()));
    }
}
