package framework.user;

public class LoginUserRequest {

    private String email;
    private String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginUserRequest from(CreateUserRequest createUserRequest) {
        return new LoginUserRequest(createUserRequest.getEmail(), createUserRequest.getPassword());
    }

    public LoginUserRequest() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
