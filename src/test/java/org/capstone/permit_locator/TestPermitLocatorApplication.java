package org.capstone.permit_locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPermitLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.from(PermitLocatorApplication::main).with(TestPermitLocatorApplication.class).run(args);
	}

}
