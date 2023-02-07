package framework.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;

public class UserCheck {

    @Step("Ответ успешный")
    public String responseSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");
    }

    @Step("Ответ неуспешный")
    public void failedMessage(ValidatableResponse response, String message, int statusCode) {
        response.assertThat()
                .body("message", equalTo(message))
                .body("success", equalTo(false))
                .and()
                .statusCode(statusCode);
    }

    @Step("Пользователь удален успешно")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(HTTP_ACCEPTED);
    }
}
