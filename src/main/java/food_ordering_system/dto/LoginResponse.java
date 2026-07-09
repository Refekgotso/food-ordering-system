package food_ordering_system.dto;

import java.util.List;

/**
 * LoginResponse is returned to the client after a successful login.
 * Contains the JWT the client must include on future requests, plus
 * basic profile info so the frontend doesn't need a second request
 * just to know who is logged in.
 */
public class LoginResponse {

    private String token;
    private String email;
    private String name;
    private List<String> roles;

    public LoginResponse(String token, String email, String name, List<String> roles) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}