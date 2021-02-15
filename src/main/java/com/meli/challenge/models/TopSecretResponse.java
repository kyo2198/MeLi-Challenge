package com.meli.challenge.models;

public class TopSecretResponse {

    private Position position;

    private String message;

    public TopSecretResponse() { }

    public TopSecretResponse(Position position, String message)
    {
        this.position = position;
        this.message = message;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
