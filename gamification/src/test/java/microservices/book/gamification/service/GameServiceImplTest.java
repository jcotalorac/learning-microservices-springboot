package microservices.book.gamification.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GameServiceImplTest {

    private GameServiceImpl gameService;

    @BeforeAll
    public void setUp() {
        gameService = new GameServiceImpl();
    }

    @Test
    public void processFirstCorrectAttemptTest() {

        Long userId = 1L;
        Long attemptId = 8L;
    }

    @Test
    public void retrieveStatsForUser() {
    }
}