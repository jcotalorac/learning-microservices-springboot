package microservices.book.gamification.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class RestClientConfiguration {

    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
