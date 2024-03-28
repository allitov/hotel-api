package com.allitov.testutils;

import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that a test class uses any of testcontainers to properly run tests.
 * @author allitov
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
public @interface EnableTestcontainers {
}
