package com.allitov.testutils;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Initializes testcontainers and adds property values to application context.
 * @author allitov
 * @version 1.0
 */
public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

    private static final KafkaContainer KAFKA =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.3"));

    private static final MongoDBContainer MONGODB =
            new MongoDBContainer(DockerImageName.parse("mongo:6.0.13"));

    static {
        POSTGRES.withReuse(true);
        POSTGRES.start();
        KAFKA.withReuse(true);
        KAFKA.start();
        MONGODB.withReuse(true);
        MONGODB.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                "spring.datasource.username=" + POSTGRES.getUsername(),
                "spring.datasource.password=" + POSTGRES.getPassword(),
                "spring.sql.init.data-locations=classpath:db/test_data.sql",
                "spring.sql.init.mode=always",
                "spring.kafka.bootstrap-servers=" + KAFKA.getBootstrapServers(),
                "spring.data.mongodb.uri=" + MONGODB.getReplicaSetUrl()
        ).applyTo(applicationContext.getEnvironment());
    }
}
