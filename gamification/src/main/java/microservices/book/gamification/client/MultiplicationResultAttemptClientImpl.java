package microservices.book.gamification.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

    private final RestTemplate restTemplate;
    private final String multiplicationHost;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public MultiplicationResultAttemptClientImpl(RestTemplate restTemplate,
                                                 @Value("${multiplicationHost}") String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationHost = multiplicationHost;
    }

    @HystrixCommand(fallbackMethod = "defaultResult")
    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(Long multiplicationId) {

        CircuitBreaker circuitBreakerMultiplicationClient = circuitBreakerFactory.create("circuitBreakerMultiplicationClient");

        return circuitBreakerMultiplicationClient.run(() -> restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationId,
                MultiplicationResultAttempt.class), throwable -> {
            System.out.println("Fallback for circuit breaker called circuitBreakerMultiplicationClient");
            return defaultResult(multiplicationId);
        });
        /*return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationId,
                MultiplicationResultAttempt.class);*/
    }

    private MultiplicationResultAttempt defaultResult(Long multiplicationId) {
        return new MultiplicationResultAttempt("fakeAlias", 10, 10, 100, true);
    }
}
