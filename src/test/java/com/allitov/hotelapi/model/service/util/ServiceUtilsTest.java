package com.allitov.hotelapi.model.service.util;

import com.allitov.hotelapi.model.entity.Hotel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceUtilsTest {

    @Test
    @DisplayName("Test copyNonNullProperties()")
    public void givenSourceAndDestination_whenCopyNonNullProperties_thenVoid() {
        Hotel source = Hotel.builder()
                .name("new name")
                .build();
        Hotel destination = Hotel.builder()
                .city("city")
                .build();

        ServiceUtils.copyNonNullProperties(source, destination);

        assertEquals("new name", destination.getName());
        assertEquals("city", destination.getCity());
    }

    @Test
    @DisplayName("Test copyNonNullProperties() NullPointerException")
    public void givenNullArguments_whenCopyNonNullProperties_thenException() {
        Hotel hotel = new Hotel();

        assertThrows(NullPointerException.class, () -> ServiceUtils.copyNonNullProperties(null, hotel));
        assertThrows(NullPointerException.class, () -> ServiceUtils.copyNonNullProperties(hotel, null));
    }
}
