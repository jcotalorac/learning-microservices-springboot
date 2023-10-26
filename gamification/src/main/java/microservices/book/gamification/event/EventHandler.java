package microservices.book.gamification.event;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.service.GameService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventHandler {

    private GameService gameService;

    public EventHandler(GameService gameService) {
        this.gameService = gameService;
    }
}
