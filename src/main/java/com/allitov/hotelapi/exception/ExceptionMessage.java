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

    public final String ROOM_NULL_HOTEL_ID = "Hotel id must be specified.";

    public final String ROOM_BLANK_DESCRIPTION = "Description must be specified.";

    public final String ROOM_INVALID_DESCRIPTION_LENGTH = "Description length must be <= {max}.";

    public final String ROOM_NULL_NUMBER = "Number must be specified.";

    public final String ROOM_NULL_PRICE = "Price must be specified.";

    public final String ROOM_INVALID_PRICE = "Price pattern must be '{regexp}'";

    public final String ROOM_NULL_MAX_PEOPLE = "Maximum number of people must be specified.";

    public final String ROOM_INVALID_MAX_PEOPLE = "Maximum number of people must be greater than zero.";

    public final String USER_BY_USERNAME_NOT_FOUND = "User with username = ''{0}'' not found.";

    public final String USER_ALREADY_EXISTS = "User with username = ''{0}'' already exists.";

    public final String USER_BY_ID_NOT_FOUND = "User with id = ''{0}'' not found.";

    public final String USER_BLANK_USERNAME = "Username must be specified.";

    public final String USER_NULL_EMAIL = "Email must be specified.";

    public final String USER_INVALID_EMAIL = "Email must be a valid email address.";

    public final String USER_BLANK_PASSWORD = "Password must be specified.";

    public final String USER_NULL_ROLE = "Role must be specified.";

    public final String USER_INVALID_ROLE = "Role must be any of ['USER', 'ADMIN'].";

    public final String BOOKING_INVALID_DATE = "'from' date can't be after 'to' date.";

    public final String BOOKING_UNAVAILABLE_DATES = "Dates from ''{0}'' to ''{1}'' are unavailable.";
}
