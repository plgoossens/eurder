package com.switchfully.eurder.service.dto;

public class CreateCredentialsDTO {

    private final String username;
    private final String password;

    public CreateCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
