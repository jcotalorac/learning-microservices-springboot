package microservices.book.gamification.client;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.stereotype.Component;

@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(Long multiplicationId) {
        return null;
    }
}
