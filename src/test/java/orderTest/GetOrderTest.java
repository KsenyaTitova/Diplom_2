package orderTest;

import framework.orders.CreateOrderRequest;
import framework.orders.Order;
import framework.orders.OrderCheck;
import framework.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class GetOrderTest {

    private final UserGenerator userFaker = new UserGenerator();
    private final User user = new User();
    private final UserCheck userCheck = new UserCheck();
    private final CreateOrderRequest createNewOrder = new CreateOrderRequest(new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa6c")));
    private final OrderCheck orderCheck = new OrderCheck();
    private final Order order = new Order();
    private final CreateUserRequest newUser = userFaker.userFaker();
    private final LoginUserRequest loginUser = new LoginUserRequest().from(newUser);
    private String accessToken = "noToken";

    @Test
    @DisplayName("Получить заказ с авторизацией")
    public void getOrderWithAuthorizationReturnTrue() {
        user.createUser(newUser);
        ValidatableResponse responseLogin = user.loginUser(loginUser);
        accessToken = userCheck.responseSuccessfully(responseLogin);
        order.createOrder(createNewOrder, accessToken);
        ValidatableResponse responseGetOrder = order.getOrderWithAuthorization(accessToken);
        orderCheck.ResponseGetOrderSuccessfully(responseGetOrder);
    }

    @Test
    @DisplayName("Получить заказ без авторизации")
    public void getOrderWithoutAuthorizationReturnCode401() {
        ValidatableResponse responseGetOrder = order.getOrderWithoutAuthorization();
        orderCheck.ResponseFailed(responseGetOrder, "You should be authorised", HTTP_UNAUTHORIZED);
    }


    @After
    public void deleteUser() {
        if (!accessToken.equals("noToken")) {
            ValidatableResponse response = user.deleteUser(accessToken);
            userCheck.deletedSuccessfully(response);
        }
    }
}
