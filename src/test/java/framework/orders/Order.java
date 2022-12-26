package framework.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class Order extends parameters.GeneralParameters {

    protected final String ROOT = "/orders";

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrder(CreateOrderRequest order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorization(CreateOrderRequest order) {
        return spec()
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Получение заказа конкретного пользователя с авторизацией")
    public ValidatableResponse getOrderWithAuthorization(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ROOT)
                .then().log().all();
    }

    @Step("Получение заказа конкретного пользователя без авторизации")
    public ValidatableResponse getOrderWithoutAuthorization() {
        return spec()
                .when()
                .get(ROOT)
                .then().log().all();
    }
}
