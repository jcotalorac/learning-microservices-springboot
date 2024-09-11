package microservices.book.gamification.controller;

import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final GameService gameService;

    public ScoreController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ScoreCard getScoreForAttempt(final Long attemptId) {
        return gameService.getScoreForAttempt(attemptId);
    }
}
