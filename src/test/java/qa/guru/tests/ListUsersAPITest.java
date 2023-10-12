package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.ListUserResponseModel;
import qa.guru.models.ListUsersDataResponseModel;
import qa.guru.models.ListUsersSupportResponseModel;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static qa.guru.spec.ListUsersSpec.listUserRequestSpec;
import static qa.guru.spec.ListUsersSpec.listUserResponseSpec;

public class ListUsersAPITest {

    @Test
    @DisplayName("Verifying receipt of successful employee list")
    void successfulListUserTest() {
        ListUserResponseModel response = step("Request for a list of users", () ->
                given()
                        .spec(listUserRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(listUserResponseSpec)
                        .extract().as(ListUserResponseModel.class));

        step("Page information", () -> {
            assertEquals(2, response.getPage());
            assertEquals(6, response.getPerPage());
            assertEquals(12, response.getTotal());
            assertEquals(2, response.getTotalPages());
        });
        step("Information about the first object on the page", () -> {
            List<ListUsersDataResponseModel> data = response.getData();

            assertEquals(7, data.get(0).getId());
            assertEquals("michael.lawson@reqres.in", data.get(0).getEmail());
            assertEquals("Michael", data.get(0).getFirstName());
            assertEquals("Lawson", data.get(0).getLastName());
            assertEquals("https://reqres.in/img/faces/7-image.jpg", data.get(0).getAvatar());
        });
        step("Information about the second object on the page", () -> {
            List<ListUsersDataResponseModel> data = response.getData();

            assertEquals(8, data.get(1).getId());
            assertEquals("lindsay.ferguson@reqres.in", data.get(1).getEmail());
            assertEquals("Lindsay", data.get(1).getFirstName());
            assertEquals("Ferguson", data.get(1).getLastName());
            assertEquals("https://reqres.in/img/faces/8-image.jpg", data.get(1).getAvatar());
        });

        step("Information about the third object on the page", () -> {
            List<ListUsersDataResponseModel> data = response.getData();

            assertEquals(9, data.get(2).getId());
            assertEquals("tobias.funke@reqres.in", data.get(2).getEmail());
            assertEquals("Tobias", data.get(2).getFirstName());
            assertEquals("Funke", data.get(2).getLastName());
            assertEquals("https://reqres.in/img/faces/9-image.jpg", data.get(2).getAvatar());
        });
        step("Information about the fourth object on the page", () -> {
            List<ListUsersDataResponseModel> data = response.getData();

            assertEquals(10, data.get(3).getId());
            assertEquals("byron.fields@reqres.in", data.get(3).getEmail());
            assertEquals("Byron", data.get(3).getFirstName());
            assertEquals("Fields", data.get(3).getLastName());
            assertEquals("https://reqres.in/img/faces/10-image.jpg", data.get(3).getAvatar());
        });
        step("Information about the fifth object on the page", () -> {
            List<ListUsersDataResponseModel> data = response.getData();

            assertEquals(11, data.get(4).getId());
            assertEquals("george.edwards@reqres.in", data.get(4).getEmail());
            assertEquals("George", data.get(4).getFirstName());
            assertEquals("Edwards", data.get(4).getLastName());
            assertEquals("https://reqres.in/img/faces/11-image.jpg", data.get(4).getAvatar());
        });
        step("Information about the sixth object on the page", () -> {
            List<ListUsersDataResponseModel> data = response.getData();

            assertEquals(12, data.get(5).getId());
            assertEquals("rachel.howell@reqres.in", data.get(5).getEmail());
            assertEquals("Rachel", data.get(5).getFirstName());
            assertEquals("Howell", data.get(5).getLastName());
            assertEquals("https://reqres.in/img/faces/12-image.jpg", data.get(5).getAvatar());
        });

        step("Support information", () -> {
            ListUsersSupportResponseModel support = response.getSupport();
            assertEquals("https://reqres.in/#support-heading", support.getUrl());
            assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", support.getText());
        });
    }
}
