package userTests;

import framework.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static java.net.HttpURLConnection.*;

public class LoginUserTest {
    private final UserGenerator userFaker  = new UserGenerator();
    private final User user = new User();
    private final UserCheck userCheck = new UserCheck ();
    private String accessToken = "noToken";
    private final CreateUserRequest newUser = userFaker.userFaker();
    private final LoginUserRequest loginUser = new LoginUserRequest().from(newUser);

    @Before
            public void setUp () {
        ValidatableResponse responseCreate = user.createUser(newUser);
        accessToken = userCheck.responseSuccessfully(responseCreate);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginUnderAnExistingUserReturnTrue () {
        ValidatableResponse responseLogin = user.loginUser(loginUser);
        userCheck.responseSuccessfully(responseLogin);
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void loginWithInvalidEmailReturnCode401 () {
        loginUser.setEmail("testTestTest@ya.ru");
        ValidatableResponse responseLogin = user.loginUser(loginUser);
        userCheck.failedMessage(responseLogin, "email or password are incorrect", HTTP_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Логин с неверным password")
    public void loginWithInvalidPasswordReturnCode401 () {
        loginUser.setPassword("testTestTest");
        ValidatableResponse responseLogin = user.loginUser(loginUser);
        userCheck.failedMessage(responseLogin, "email or password are incorrect", HTTP_UNAUTHORIZED);
    }

    @After
    public void deleteUser() {
        if (!accessToken.equals("noToken")) {
            ValidatableResponse response = user.deleteUser(accessToken);
            userCheck.deletedSuccessfully(response);
        }
    }
}
