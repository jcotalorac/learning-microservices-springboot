package microservices.book.gamification.controller;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserStatsControllerTest {

    @Mock
    private GameServiceImpl gameService;
    private UserStatsController userStatsController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        userStatsController = new UserStatsController(gameService);
    }

    @Test
    public void retrieveUserStats() {
        Long userId = 2L;
        int userScore = 100;

        given(gameService.retrieveStatsForUser(userId))
                .willReturn(new GameStats(userId, userScore,
                        List.of(Badge.BRONZE_MULTIPLICATOR, Badge.SILVER_MULTIPLICATOR)));

        GameStats statsForUser = userStatsController.getStatsForUser(userId);

        assertThat(statsForUser.getScore()).isEqualTo(userScore);
        assertThat(statsForUser.getBadges())
                .contains(Badge.BRONZE_MULTIPLICATOR, Badge.SILVER_MULTIPLICATOR);
    }
}