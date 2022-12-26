package framework.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class OrderCheck {

    @Step("Ответ успешный")
    public void ResponseSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .body("order", notNullValue())
                .and()
                .statusCode(HTTP_OK);
    }

    @Step("Ответ неуспешный")
    public void ResponseFailed(ValidatableResponse response, String message, int statusCode) {
        response.assertThat()
                .body("message", equalTo(message))
                .body("success", equalTo(false))
                .and()
                .statusCode(statusCode);
    }

    @Step("В ответе получен код 500 (InternalServerError)")
    public void ResponseInternalServerError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Ответ успешный")
    public void ResponseGetOrderSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .and()
                .statusCode(HTTP_OK);
    }
}
