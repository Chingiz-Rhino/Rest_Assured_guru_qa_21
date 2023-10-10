package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.CreateUserBodyModel;
import qa.guru.models.CreateUserResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static qa.guru.spec.CreateUserSpec.createUserRequestSpec;
import static qa.guru.spec.CreateUserSpec.createUserResponseSpec;

public class CreateUserAPITest {
    @Test
    @DisplayName("Успешное создание пользователя")
    void successfulUserCreateTest() {

        CreateUserBodyModel createdUser = new CreateUserBodyModel();
        createdUser.setName("Ezio Auditore");
        createdUser.setJob("Assassin");

        CreateUserResponseModel response = step("Create user request", ()->
        given(createUserRequestSpec)
                .body(createdUser)
                .when()
                .post("/users")
                .then()
                .spec(createUserResponseSpec)
                .extract().as(CreateUserResponseModel.class));

        step("",()->{
           assertEquals("Ezio Auditore", response.getName());
           assertEquals("Assassin", response.getJob());
           assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
}
