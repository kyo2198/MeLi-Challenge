package com.meli.challenge.models;

public class Position {

    private double x;
    private double y;

    public Position()
    {
    }

    public Position(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int result = 21;

        result = 31 * result + Double.valueOf(x).hashCode();

        result = 31 * result + Double.valueOf(y).hashCode();

        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if ((object instanceof Position) == false)
            return false;

        if (getClass() != object.getClass())
            return false;

        Position otherObject = (Position) object;

        if (x != otherObject.getX())
            return false;

        if (y != otherObject.getY())
            return false;

        return true;
    }
}