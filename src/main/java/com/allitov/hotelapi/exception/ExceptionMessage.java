package com.allitov.hotelapi.exception;

import lombok.experimental.UtilityClass;

/**
 * The utility class that contains string constants with exception messages.
 * @author allitov
 */
@UtilityClass
public class ExceptionMessage {

    public final String HOTEL_BY_ID_NOT_FOUND = "Hotel with id = ''{0}'' not found.";

    public final String HOTEL_BLANK_NAME = "Name must be specified.";

    public final String HOTEL_INVALID_NAME_LENGTH = "Name length must be <= {max}.";

    public final String HOTEL_BLANK_DESCRIPTION = "Description must be specified.";

    public final String HOTEL_INVALID_DESCRIPTION_LENGTH = "Description length must be <= {max}.";

    public final String HOTEL_BLANK_CITY = "City must be specified.";

    public final String HOTEL_INVALID_CITY_LENGTH = "City length must be <= {max}.";

    public final String HOTEL_BLANK_ADDRESS = "Address must be specified.";

    public final String HOTEL_INVALID_ADDRESS_LENGTH = "Address length must be <= {max}.";

    public final String HOTEL_BLANK_DISTANCE_FROM_CENTER = "Distance from center must be specified.";

    public final String ROOM_BY_ID_NOT_FOUND = "Room with id = ''{0}'' not found.";
}
