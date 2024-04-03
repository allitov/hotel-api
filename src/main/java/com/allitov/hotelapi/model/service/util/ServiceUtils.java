package com.allitov.hotelapi.model.service.util;

import com.allitov.hotelapi.exception.CopyPropertiesException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * The utility class that provides different helper methods for services.
 * @author allitov
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class ServiceUtils {

    /**
     * Copies nonnull properties from the source object to the destination object.
     * @param source the object to copy properties to.
     * @param destination the object to copy properties from.
     * @throws NullPointerException if the source or the destination is null.
     * @throws CopyPropertiesException if the source or the destination {@link Field} object
     * is enforcing Java language access control and the underlying field is inaccessible.
     */
    public void copyNonNullProperties(@NonNull Object source, @NonNull Object destination) {
        Class<?> sourceClass = source.getClass();
        Field[] fields = sourceClass.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(source);

                if (value != null) {
                    field.set(destination, value);
                }
            }
        } catch (IllegalAccessException e) {
            log.warn("Exception was thrown during copyNonNullProperties() method: {}", e.getMessage());
            throw new CopyPropertiesException(e.getMessage());
        }
    }
}
