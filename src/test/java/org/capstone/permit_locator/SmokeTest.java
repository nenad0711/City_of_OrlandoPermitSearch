package org.capstone.permit_locator;
/**
 * This class performs a smoke test for the application context, ensuring that the necessary beans are loaded.
 * It is annotated with Spring Boot's @SpringBootTest to initialize the Spring Boot application context for testing.
 */
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

    /**
     * The PermitController instance to be tested.
     */
    @Autowired
    private PermitController controller;
    /**
     * Verifies that the Spring application context loads successfully and the PermitController bean is not null.
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}