package qa.guru.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.models.SingleResourceResponseModel;
import qa.guru.models.SingleResourceSupportResponseModel;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static qa.guru.spec.SingleResourceSpec.*;

public class SingleResourceAPITest {
    @Test
    @DisplayName("Single resource")
    void singleResourceTest() {
        SingleResourceResponseModel response = step("Single resource request", () ->
                given(singleResourceRequestSpec)
                        .when()
                        .get("/unknown/2")
                        .then()
                        .spec(singleResourceResponseSpec)
                        .extract().as(SingleResourceResponseModel.class));


        step("Verify response. User information", () -> {
            SingleResourceResponseModel data = response.getData();
            assertEquals(2, data.getId());
            assertEquals("fuchsia rose", data.getName());
            assertEquals(2001, data.getYear());
            assertEquals("#C74375", data.getColor());
            assertEquals("17-2031", data.getPantoneValue());
        });
        step("Support information", () -> {
            SingleResourceSupportResponseModel support = response.getSupport();
            assertEquals("https://reqres.in/#support-heading", support.getUrl());
            assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", support.getText());


        });

    }

    @Test
    @DisplayName("Can't find single resource")
    void singleResourceNotFoundTest() {

        given(singleResourceRequestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(singleResource404ResponseSpec);

    }
}
