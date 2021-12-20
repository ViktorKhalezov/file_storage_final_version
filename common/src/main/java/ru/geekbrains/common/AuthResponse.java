package ru.geekbrains.common;

public class AuthResponse extends AbstractMessage {
    private String response;

    public AuthResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}

