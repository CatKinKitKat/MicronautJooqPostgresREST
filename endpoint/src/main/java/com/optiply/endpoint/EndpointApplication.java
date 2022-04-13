package com.optiply.endpoint;

import io.micronaut.runtime.Micronaut;

/**
 * Main Class for the application.
 */
public class EndpointApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments (not used)
	 */
	public static void main(String[] args) {
		Micronaut.run(EndpointApplication.class, args);
	}
}
