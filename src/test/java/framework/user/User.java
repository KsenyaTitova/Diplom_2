package framework.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class User extends parameters.GeneralParameters {

    protected final String ROOT = "/auth";

    @Step ("Создание пользователя")
    public ValidatableResponse createUser (CreateUserRequest user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "/register")
                .then().log().all();
    }

    @Step ("Авторизация пользователя")
    public ValidatableResponse loginUser (LoginUserRequest user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT +"/login")
                .then().log().all();
    }

    @Step ("Изменение данных пользователя с авторизацией")
    public ValidatableResponse changeUser (ChangeUserRequest user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }

    @Step ("Изменение данных пользователя без авторизации")
    public ValidatableResponse changeUserWithoutAuthorisation (ChangeUserRequest user) {
        return spec()
                .body(user)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }

    @Step ("Удаление пользователя")
    public ValidatableResponse deleteUser (String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(ROOT + "/user")
                .then().log().all();
    }
}
