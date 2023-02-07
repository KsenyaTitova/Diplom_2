package framework.user;

public class ChangeUserRequest {

    private String email;
    private String password;
    private String name;

    public ChangeUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public ChangeUserRequest() {
    }

    public static ChangeUserRequest from(CreateUserRequest createUserRequest) {
        return new ChangeUserRequest(createUserRequest.getEmail(), createUserRequest.getPassword(), createUserRequest.getName());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
