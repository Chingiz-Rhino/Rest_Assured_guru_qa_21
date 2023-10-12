package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.DeleteUserBodyModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static qa.guru.spec.DeleteUserSpec.deleteUserRequestSpec;
import static qa.guru.spec.DeleteUserSpec.deleteUserResponseSpec;

public class DeleteUserAPITest {

    @Test
    @DisplayName("Successful user deletion")
    void successfulUserDeleteTest() {
        DeleteUserBodyModel deleteUser = new DeleteUserBodyModel();

        step("Delete user", ()->
            given(deleteUserRequestSpec)
                    .body(deleteUser)
                    .when()
                    .delete("/users/2")
                    .then()
                    .spec(deleteUserResponseSpec));

    }
}
