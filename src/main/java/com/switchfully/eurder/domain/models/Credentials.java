package com.switchfully.eurder.domain.models;

public class Credentials {

    private final String username;
    private final String password;
    private final Role role;
    private final String customerId;

    public Credentials(String username, String password, Role role, String customerId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean doesPasswordMatch(String password){
        return this.password.equals(password);
    }
}
