package microservices.book.gamification.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
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
            log.error("Fallback for circuit breaker called circuitBreakerMultiplicationClient", throwable);
            return defaultResult(multiplicationId);
        });
        /*return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationId,
                MultiplicationResultAttempt.class);*/
    }

    private MultiplicationResultAttempt defaultResult(Long multiplicationId) {
        return new MultiplicationResultAttempt("fakeAlias", 10, 10, 100, true);
    }
}
