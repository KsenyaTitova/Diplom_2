package userTests;

import framework.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class ChangeUserTest {

    private final UserGenerator userFaker = new UserGenerator();
    private final User user = new User();
    private final UserCheck userCheck = new UserCheck();
    private final CreateUserRequest newUser = userFaker.userFaker();
    private final ChangeUserRequest changeUser = new ChangeUserRequest().from(newUser);
    private final LoginUserRequest loginUser = new LoginUserRequest().from(newUser);
    private String accessToken = "noToken";

    @Before
    public void setUp() {
        changeUser.setEmail("testTestTest@ya.ru");
        changeUser.setPassword("testTestTest");
        changeUser.setName("test test");
    }

    @Test
    @DisplayName("Изменить данные пользователя с авторизацией")
    public void changeUserDataWithAuthorizationReturnTrue() {
        user.createUser(newUser);
        ValidatableResponse responseLogin = user.loginUser(loginUser);
        accessToken = userCheck.responseSuccessfully(responseLogin);
        ValidatableResponse responseChange = user.changeUser(changeUser, accessToken);
        userCheck.responseSuccessfully(responseChange);
    }

    @Test
    @DisplayName("Изменить данные пользователя без авторизации")
    public void changeUserDataWithoutAuthorizationReturnCode401() {
        ValidatableResponse responseChange = user.changeUserWithoutAuthorisation(changeUser);
        userCheck.failedMessage(responseChange, "You should be authorised", HTTP_UNAUTHORIZED);
    }

    @After
    public void deleteUser() {
        if (!accessToken.equals("noToken")) {
            ValidatableResponse response = user.deleteUser(accessToken);
            userCheck.deletedSuccessfully(response);
        }
    }
}
