package com.devops.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.SpringApplication;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void helloEndpointShouldReturnMessage() {
        String response = restTemplate.getForObject(
                "http://localhost:" + port + "/",
                String.class
        );

        assertThat(response).isEqualTo("Hello from DevOps Java API!");
    }

    @Test
    void healthEndpointShouldReturnUp() {
        String response = restTemplate.getForObject(
                "http://localhost:" + port + "/health",
                String.class
        );

        assertThat(response).isEqualTo("UP");
    }

    @Test
    void applicationClassShouldExist() {
        assertThat(Application.class).isNotNull();
}

@Test
void applicationMainShouldStartApplication() {
    try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

        Application.main(new String[]{});

        mocked.verify(() ->
                SpringApplication.run(Application.class, new String[]{})
        );
    }
}
}