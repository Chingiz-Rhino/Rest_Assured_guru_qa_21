package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.CreateUserBodyModel;
import qa.guru.models.CreateUserResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static qa.guru.spec.CreateUserSpec.createUserRequestSpec;
import static qa.guru.spec.CreateUserSpec.createUserResponseSpec;

public class CreateUserAPITest {
    @Test
    @DisplayName("Успешное создание пользователя")
    void successfulUserCreateTest() {

        CreateUserBodyModel createdUser = new CreateUserBodyModel();
        createdUser.setName("Ezio Auditore");
        createdUser.setJob("Assassin");

        CreateUserResponseModel response = step("Create user request", () ->
                given(createUserRequestSpec)
                        .body(createdUser)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Verify response", () -> {
            assertEquals("Ezio Auditore", response.getName());
            assertEquals("Assassin", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Успешное создание пользователя без Name")
    void successfulUserCreateWithoutNameTest() {

        CreateUserBodyModel createdUser = new CreateUserBodyModel();
        createdUser.setJob("Assassin");

        CreateUserResponseModel response = step("Create user without name request", () ->
                given(createUserRequestSpec)
                        .body(createdUser)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Verify response", () -> {
            assertNull(response.getName());
            assertEquals("Assassin", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
    @Test
    @DisplayName("Успешное создание пользователя без Job")
    void successfulUserCreateWithoutJobTest() {

        CreateUserBodyModel createdUser = new CreateUserBodyModel();
        createdUser.setName("Ezio Auditore");

        CreateUserResponseModel response = step("Create user without job request", () ->
                given(createUserRequestSpec)
                        .body(createdUser)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Verify response", () -> {
            assertEquals("Ezio Auditore" ,response.getName());
            assertNull(response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Успешное создание пользователя без Name и Job")
    void successfulUserCreateWithoutNameAndJobTest() {

        CreateUserBodyModel createdUser = new CreateUserBodyModel();

        CreateUserResponseModel response = step("Create user without name and job request", () ->
                given(createUserRequestSpec)
                        .body(createdUser)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Verify response", () -> {
            assertNull(response.getName());
            assertNull(response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
}
