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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


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
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(List.of(Badge.FIRST_WON));

        GameStats gameIteration = gameService.newAttemptForUser(userId, attemptId, true);

        assertThat(gameIteration.getScore()).isEqualTo(totalScore);
        assertThat(gameIteration.getBadges()).isEqualTo(Collections.singletonList(Badge.FIRST_WON));
    }

    @Test
    public void processWrongAttemptTest() {

        Long userId = 2L;
        Long attemptId = 9L;
        int totalScore = 0;

        GameStats gameIteration = gameService.newAttemptForUser(userId, attemptId, false);

        assertThat(gameIteration.getScore()).isEqualTo(totalScore);
        assertThat(gameIteration.getBadges()).isEmpty();

        verify(scoreCardRepository, never()).getTotalScoreForUser(userId);
        verify(badgeCardRepository, never()).findByUserIdOrderByBadgeTimestampDesc(userId);
    }

    @Test
    public void retrieveStatsForUserTest() {
    }
}