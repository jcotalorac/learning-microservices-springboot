package microservices.book.gamification.controller;

import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class UserStatsController {

    private GameService gameService;

    public UserStatsController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GameStats getStatsForUser(Long userId) {
        return gameService.retrieveStatsForUser(userId);
    }
}
