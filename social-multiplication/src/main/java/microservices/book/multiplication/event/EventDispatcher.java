package microservices.book.multiplication.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;
    private String multiplicationExchange;
    private String multiplicationSolvedRoutingKey;
}
