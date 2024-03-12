package org.capstone.permit_locator;
/**
 * Test configuration class for the PermitLocatorApplication.
 * This class provides the main method to bootstrap the Spring application context
 * for testing purposes.
 *
 * @author [Nenad Jovanovic]
 * @version 1.0
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPermitLocatorApplication {
	/**
	 * The main method to bootstrap the Spring application context for testing purposes.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.from(PermitLocatorApplication::main).with(TestPermitLocatorApplication.class).run(args);
	}

}
