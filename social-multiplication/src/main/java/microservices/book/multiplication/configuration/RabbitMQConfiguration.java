package microservices.book.multiplication.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public TopicExchange multiplicationExchange(final String exchangeName) {
        return new TopicExchange(exchangeName);
    }
}
