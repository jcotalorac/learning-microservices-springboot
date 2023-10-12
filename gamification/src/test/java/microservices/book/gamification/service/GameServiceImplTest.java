package microservices.book.gamification.service;

import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;
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

        assertThat(gameIteration.getScore()).isEqualTo(totalScore);
    }

    @Test
    public void retrieveStatsForUser() {
    }
}