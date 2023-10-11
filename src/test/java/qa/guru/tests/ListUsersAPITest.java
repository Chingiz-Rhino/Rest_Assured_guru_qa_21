package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.ListUserResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static qa.guru.spec.ListUsersSpec.listUserRequestSpec;
import static qa.guru.spec.ListUsersSpec.listUserResponseSpec;

public class ListUsersAPITest {

    @Test
    @DisplayName("Проверка получения успешного списка сотрудников")
    void successfulListUserTest() {
        ListUserResponseModel response = step("Request for a list of users", () ->
                given()
                        .spec(listUserRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(listUserResponseSpec)
                        .extract().as(ListUserResponseModel.class));
    }
}
