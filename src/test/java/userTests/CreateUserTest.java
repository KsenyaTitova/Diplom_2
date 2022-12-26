package userTests;

import framework.user.CreateUserRequest;
import framework.user.User;
import framework.user.UserCheck;
import framework.user.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;

public class CreateUserTest {
    private final UserGenerator userFaker = new UserGenerator();
    private final User user = new User();
    private final UserCheck userCheck = new UserCheck();
    private String accessToken = "noToken";
    private final CreateUserRequest newUser = userFaker.userFaker();

    @Test
    @DisplayName("Создание нового пользователя")
    public void createANewUserReturnTrue() {
        ValidatableResponse response = user.createUser(newUser);
        accessToken = userCheck.responseSuccessfully(response);
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void createADoubleUserReturnCode403() {
        ValidatableResponse response = user.createUser(newUser);
        accessToken = userCheck.responseSuccessfully(response);
        ValidatableResponse responseDouble = user.createUser(newUser);
        userCheck.failedMessage(responseDouble, "User already exists", HTTP_FORBIDDEN);
    }

    @Test
    @DisplayName("Создание пользователя без email")
    public void createANewUserWithoutEmailReturnCode403() {
        newUser.setEmail(null);
        ValidatableResponse response = user.createUser(newUser);
        userCheck.failedMessage(response, "Email, password and name are required fields", HTTP_FORBIDDEN);
    }

    @Test
    @DisplayName("Создание пользователя без password")
    public void createANewUserWithoutPasswordReturnCode403() {
        newUser.setPassword(null);
        ValidatableResponse response = user.createUser(newUser);
        userCheck.failedMessage(response, "Email, password and name are required fields", HTTP_FORBIDDEN);
    }

    @Test
    @DisplayName("Создание пользователя без name")
    public void createANewUserWithoutNameReturnCode403() {
        newUser.setName(null);
        ValidatableResponse response = user.createUser(newUser);
        userCheck.failedMessage(response, "Email, password and name are required fields", HTTP_FORBIDDEN);
    }

    @After
    public void deleteUser() {
        if (!accessToken.equals("noToken")) {
            ValidatableResponse response = user.deleteUser(accessToken);
            userCheck.deletedSuccessfully(response);
        }
    }
}
