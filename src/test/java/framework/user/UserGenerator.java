package framework.user;

import com.github.javafaker.Faker;

public class UserGenerator {

    private final Faker faker = new Faker();

    public CreateUserRequest userFaker() {
        return new CreateUserRequest(faker.internet().emailAddress(), faker.internet().password(), faker.name().name());
    }
}
