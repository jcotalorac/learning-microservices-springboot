package microservices.book.gamification.controller;

import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.GameService;

public class UserStatsController {

    private GameService gameService;

    public UserStatsController(GameService gameService) {
        this.gameService = gameService;
    }

    public GameStats getStatsForUser(Long userId) {
        return null;
    }
}
