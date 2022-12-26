package orderTest;

import com.github.javafaker.Faker;
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
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

public class CreateOrderTest {

    private final UserGenerator userFaker = new UserGenerator();
    private final User user = new User();
    private final UserCheck userCheck = new UserCheck();
    private final CreateOrderRequest createNewOrder = new CreateOrderRequest(new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa6c")));
    private final OrderCheck orderCheck = new OrderCheck();
    private final Order order = new Order();
    private final CreateUserRequest newUser = userFaker.userFaker();
    private final LoginUserRequest loginUser = new LoginUserRequest().from(newUser);
    private final Faker faker = new Faker();
    private String accessToken = "noToken";

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createANewOrderWithAuthorizationReturnTrue() {
        user.createUser(newUser);
        ValidatableResponse responseLogin = user.loginUser(loginUser);
        accessToken = userCheck.responseSuccessfully(responseLogin);
        ValidatableResponse response = order.createOrder(createNewOrder, accessToken);
        orderCheck.ResponseSuccessfully(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createANewOrderWithoutAuthorizationReturnTrue() {
        ValidatableResponse response = order.createOrderWithoutAuthorization(createNewOrder);
        orderCheck.ResponseSuccessfully(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createANewOrderWithoutIngredientsReturnCode400() {
        createNewOrder.setIngredients(null);
        ValidatableResponse response = order.createOrderWithoutAuthorization(createNewOrder);
        orderCheck.ResponseFailed(response, "Ingredient ids must be provided", HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа  неверным хэш ингредиентов")
    public void createANewOrderWithInvalidHashIngredientsReturnCode500() {
        createNewOrder.addIngredient(faker.internet().password(23, 24));
        ValidatableResponse response = order.createOrderWithoutAuthorization(createNewOrder);
        orderCheck.ResponseInternalServerError(response);
    }

    @After
    public void deleteUser() {
        if (!accessToken.equals("noToken")) {
            ValidatableResponse response = user.deleteUser(accessToken);
            userCheck.deletedSuccessfully(response);
        }
    }
}
