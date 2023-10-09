package qa.guru;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.base.TestBase;
import qa.guru.models.LoginBodyModel;
import qa.guru.models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqResAPITest extends TestBase {

    @Test
    @DisplayName("Successful authorization")
    void successfulAuthTest() {

        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    @DisplayName("Unsuccessful authorization")
    void unsuccessfulAuthTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("");
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Successful user creation")
    void successfulUserCreateTest() {
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
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Ezio Auditore"))
                .body("job", is("Assassin"))
                .body("id", notNullValue());
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
