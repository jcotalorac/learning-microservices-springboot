package microservices.book.gamification.service;

import microservices.book.gamification.domain.GameStats;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceImplTest {

    private GameServiceImpl gameService;

    @BeforeAll
    public void setUp() {
        gameService = new GameServiceImpl();
    }

    @Test
    public void processFirstCorrectAttemptTest() {

        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;

        GameStats gameIteration = gameService.newAttemptForUser(userId, attemptId, true);
    }

    @Test
    public void retrieveStatsForUser() {
    }
}