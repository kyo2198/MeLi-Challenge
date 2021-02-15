package com.meli.challenge.models;

import javax.persistence.AttributeConverter;

public class PositionConverter implements AttributeConverter<Position, String> {

    private static final String SEPARATOR = "#SEPARATOR#";

    @Override
    public String convertToDatabaseColumn(Position position) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(position.getX())
                     .append(SEPARATOR)
                     .append(position.getY());

        return stringBuilder.toString();
    }

    @Override
    public Position convertToEntityAttribute(String positionString) {
        String[] positionStringSplited = positionString.split(SEPARATOR);

        return new Position(Double.parseDouble(positionStringSplited[0]), Double.parseDouble(positionStringSplited[1]));
    }
}