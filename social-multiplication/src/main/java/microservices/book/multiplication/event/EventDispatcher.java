package microservices.book.multiplication.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;
    private String multiplicationExchange;
    private String multiplicationSolvedRoutingKey;

    @Autowired
    public EventDispatcher(final RabbitTemplate rabbitTemplate,
                           final String multiplicationExchange,
                           final String multiplicationSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.multiplicationExchange = multiplicationExchange;
        this.multiplicationSolvedRoutingKey = multiplicationSolvedRoutingKey;
    }
}
