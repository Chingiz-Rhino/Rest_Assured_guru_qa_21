package qa.guru;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.base.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqResAPITest extends TestBase {

    @Test
    @DisplayName("")
    void successfulAuth() {
        String authData = "{ \"email\" :  \"eve.holt@reqres.in\", \"password\" : \"cityslicka\" }";

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
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulAuth() {
        String authData = "{ \"email\" :  \"eve.holt@reqres.in\", \"password\" : \"\" }";

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
    void successfulUserCreate() {
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
    void successfulUserDelete() {
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
    void singleResource() {

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
    void singleResourceNotFound() {

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
    void userUpdate() {
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
