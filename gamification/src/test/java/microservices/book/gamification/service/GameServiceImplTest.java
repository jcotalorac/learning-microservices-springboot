package microservices.book.gamification.service;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;
    @Mock
    private BadgeCardRepository badgeCardRepository;
    private GameServiceImpl gameService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository);
    }

    @Test
    public void processFirstCorrectAttemptTest() {

        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;

        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);

        GameStats gameIteration = gameService.newAttemptForUser(userId, attemptId, true);

        assertThat(gameIteration.getScore()).isEqualTo(totalScore);
        assertThat(gameIteration.getBadges()).isEqualTo(Collections.singletonList(Badge.FIRST_WON));
    }

    @Test
    public void retrieveStatsForUser() {
    }
}