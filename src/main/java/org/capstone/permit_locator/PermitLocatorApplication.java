package org.capstone.permit_locator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for Permit Locator.
 * This class initializes the Spring Boot application context and starts the application.
 */
@SpringBootApplication
public class PermitLocatorApplication {
	/**
	 * The main method to start the Permit Locator application.
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PermitLocatorApplication.class, args);
	}

}

