package com.meli.challenge.models;

public class TopSecretSplitResponse {

    private Position position;

    private String message;

    public TopSecretSplitResponse() { }

    public TopSecretSplitResponse(Position position, String message)
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