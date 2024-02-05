package microservices.book.gamification.controller;

import microservices.book.gamification.domain.ScoreCard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    @GetMapping("/{attemptId}")
    public ScoreCard getScoreForAttempt() {
        return new ScoreCard();
    }
}
