package com.meli.challenge.models;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.List;

@Entity
public class Satellite {

    @Id
    @Column
    private String name;

    @Column
    private int index;

    @Column
    private double distance;

    @Column
    @Convert(converter = MessageConverter.class)
    private List<String> message;

    @Column
    @Convert(converter = PositionConverter.class)
    private Position position;

    public Satellite() {}

    public Satellite(String name, int index, double distance, List<String> message, Position position)
    {
        this.name = name;
        this.index = index;
        this.distance = distance;
        this.message = message;
        this.position = position;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setIndex(int order)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setMessage(List<String> message)
    {
        this.message = message;
    }

    public List<String> getMessage()
    {
        return message;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public Position getPosition()
    {
        return this.position = position;
    }
}