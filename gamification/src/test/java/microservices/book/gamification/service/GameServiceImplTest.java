package microservices.book.gamification.service;

import microservices.book.gamification.client.MultiplicationResultAttemptClientImpl;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;
    @Mock
    private BadgeCardRepository badgeCardRepository;

    @Mock
    private MultiplicationResultAttemptClientImpl multiplicationResultAttemptClient;
    private GameServiceImpl gameService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository, multiplicationResultAttemptClient);

        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("john_doe",
                41, 10, 410, true);
        given(multiplicationResultAttemptClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);
    }

    @Test
    public void processFirstCorrectAttemptTest() {

        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;

        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(List.of());
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(List.of(new ScoreCard(userId, attemptId)));

        GameStats gameIteration = gameService.newAttemptForUser(userId, attemptId, true);

        assertThat(gameIteration.getScore()).isEqualTo(totalScore);
        assertThat(gameIteration.getBadges()).isEqualTo(Collections.singletonList(Badge.FIRST_WON));
    }

    @Test
    public void processBronzeBadgeUserAchievementTest() {

        Long userId = 4L;
        Long attemptId = 6L;
        int totalScore = 100;

        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(List.of(new BadgeCard(userId, Badge.FIRST_WON)));
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(IntStream.range(0, 10)
                        .mapToObj(i -> new ScoreCard(userId, (long) i))
                        .collect(Collectors.toList()));

        GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).contains(Badge.BRONZE_MULTIPLICATOR);
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

        Long userId = 3L;
        int totalScore = 1000;
        BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);

        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(List.of(badgeCard));

        GameStats gameStats = gameService.retrieveStatsForUser(userId);

        assertThat(gameStats.getScore()).isEqualTo(totalScore);
        assertThat(gameStats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
    }

    @Test
    public void processCorrectAttemptForLuckyNumberBadgeTest() {

        Long userId = 4L;
        Long attemptId = 5L;

        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(List.of());

        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("john_doe",
                42, 10, 420, true);
        given(multiplicationResultAttemptClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        GameStats gameIteration = gameService.newAttemptForUser(userId, attemptId, true);

        assertThat(gameIteration.getBadges()).containsOnly(Badge.LUCKY_NUMBER);
    }
}