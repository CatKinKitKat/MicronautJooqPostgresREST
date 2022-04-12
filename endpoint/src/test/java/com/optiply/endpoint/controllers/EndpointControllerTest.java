package com.optiply.endpoint.controllers;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * The type Endpoint controller test.
 */
@MicronautTest
public class EndpointControllerTest {

    /**
     * The Client.
     */
    @Inject
    @Client("/")
    HttpClient client;

    /**
     * The Endpoint controller.
     */
    @Inject
    EndpointController endpointController;


    /**
     * Endpoint controller mock endpoint controller.
     *
     * @return the endpoint controller
     */
    @MockBean(EndpointController.class)
    EndpointController endpointControllerMock() {
        return mock(EndpointController.class);
    }

    /**
     * Test endpoint.
     */
    @Test
    void testEndpoint() {

    }

}
