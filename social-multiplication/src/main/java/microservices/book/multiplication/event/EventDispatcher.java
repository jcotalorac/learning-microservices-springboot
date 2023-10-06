package microservices.book.multiplication.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;
    private String multiplicationExchange;
    private String multiplicationSolvedRoutingKey;

    @Autowired
    public EventDispatcher(RabbitTemplate rabbitTemplate, String multiplicationExchange, String multiplicationSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.multiplicationExchange = multiplicationExchange;
        this.multiplicationSolvedRoutingKey = multiplicationSolvedRoutingKey;
    }
}
